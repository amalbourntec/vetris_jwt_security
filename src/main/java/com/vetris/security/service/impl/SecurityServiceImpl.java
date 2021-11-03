package com.vetris.security.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetris.security.dto.CommonResponseDTO;
import com.vetris.security.enums.ExceptionCodes;
import com.vetris.security.enums.StatusType;
import com.vetris.security.exception.InvalidUserException;
import com.vetris.security.exception.TokenExpiredException;
import com.vetris.security.exception.UnauthorizedException;
import com.vetris.security.model.User;
import com.vetris.security.model.UserRoles;
import com.vetris.security.repository.SecurityRepository;
import com.vetris.security.repository.UserRolesRepostitory;
import com.vetris.security.service.SecurittyService;
import com.vetris.security.utils.MfaUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Donepudi Suresh
 *
 */
@Service
public class SecurityServiceImpl implements SecurittyService {

	private static final String AUTHORIZATION = "AUTHORIZATION";
	private static final String USERUUID = "id";
	private static final String USERROLES = "roles";
	private static final String MFAENABLE = "mfaEnabled";
	private static final String MFAVALIDATED = "mfaValidated";
	private static final String CLAIMSUBJECT = "sub";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.token.validity}")
	private int jwtTokenValidity;

	@Autowired
	SecurityRepository securityRepository;

	@Autowired
	UserRolesRepostitory userRolesRepostitory;

	@Autowired
	ObjectMapper objectMapper;

	/**
	 * Method to login with username and password
	 * 
	 * @param auth
	 * @throws UnauthorizedException
	 * @throws InvalidUserException
	 * @throws TokenExpiredException
	 */
	@Override
	public ResponseEntity<CommonResponseDTO> signon(String auth)
			throws UnauthorizedException, InvalidUserException, TokenExpiredException {
		CommonResponseDTO resultDto = new CommonResponseDTO();
		HttpHeaders responseHeaders = new HttpHeaders();
		auth = auth.replace("Basic ", "");
		byte[] baseDecode = Base64.getDecoder().decode(auth);
		String[] userCred = new String(baseDecode).split(":");
		User resultUser = securityRepository.findByLoginId(userCred[0]);
		if (!Objects.nonNull(resultUser)) {
			throw new InvalidUserException(ExceptionCodes.USER_NOT_FOUND.getMessage());
		}
		if (!resultUser.getPassword().equals(encodePassword(userCred[1]))) {
			throw new UnauthorizedException(ExceptionCodes.UNAUTHORIZED_USER.getMessage());
		}
		Map<String, Object> claims = new HashMap<>();
		claims.put(USERUUID, resultUser.getId());
		Optional<UserRoles> userRoles = userRolesRepostitory.findById(resultUser.getUserRoleId());
		if (userRoles.isPresent()) {
			claims.put(USERROLES, "ROLE_" + userRoles.get().getCode());
		}
		if(Objects.nonNull(resultUser.getEnableMfa()) && resultUser.getEnableMfa().equals("Y")) {
			claims.put(MFAENABLE,resultUser.getEnableMfa());
			claims.put(MFAVALIDATED, "false");
		}
		String jwtToken = doGenerateToken(claims, userCred[0]);
		responseHeaders.set(AUTHORIZATION, jwtToken);
		responseHeaders.set("Access-Control-Expose-Headers", AUTHORIZATION);

		resultDto.setStatus(StatusType.SUCCESS.getMessage());
		resultDto.setPayload(claims);
		resultDto.setMessage("User logged in successfully");
		return ResponseEntity.ok().headers(responseHeaders).body(resultDto);
	}

	/**
	 * Method to decode the given JWT token using secret
	 * 
	 * @throws TokenExpiredException
	 * @throws UnauthorizedException 
	 */
	@Override
	public Claims decodeToken(String token) throws TokenExpiredException, UnauthorizedException {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			throw new TokenExpiredException(ExceptionCodes.TOKEN_EXPIRED.getMessage());
		}
		
		return claims;
	}

	/**
	 * Method to validate MFA token the given JWT token using secret
	 * 
	 * @throws TokenExpiredException
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 * @throws UnauthorizedException 
	 */
	@Override
	public ResponseEntity<CommonResponseDTO> mfasignon(String token, String otp) throws TokenExpiredException, UnauthorizedException {
		CommonResponseDTO resultDto = new CommonResponseDTO();
		HttpHeaders responseHeaders = new HttpHeaders();
		
		Claims claims= decodeToken(token);
				
		if(!Objects.nonNull(claims.get(MFAENABLE)) && !claims.get(MFAENABLE).equals("Y") && !claims.get(MFAVALIDATED).equals("false")) {
			throw new UnauthorizedException(ExceptionCodes.MFA_NOT_VALIDATED.getMessage());
		}
		
		User resultUser = securityRepository.findByLoginId(claims.get(CLAIMSUBJECT).toString());
		
		if (!validateOtp(resultUser.getSecretKey(), otp)) {
			throw new UnauthorizedException(ExceptionCodes.INVALID_OTP.getMessage());
		}
		//Update Claims data
		claims.put(MFAENABLE,resultUser.getEnableMfa());
		claims.put(MFAVALIDATED, "true");
		
		String jwtToken = doGenerateToken(claims, claims.get(CLAIMSUBJECT).toString());
		responseHeaders.set(AUTHORIZATION, jwtToken);
		responseHeaders.set("Access-Control-Expose-Headers", AUTHORIZATION);

		resultDto.setStatus(StatusType.SUCCESS.getMessage());
		resultDto.setPayload(claims);
		resultDto.setMessage("User logged in successfully");
		return ResponseEntity.ok().headers(responseHeaders).body(resultDto);
	}

	/**
	 * Method to create JWT token using given parameters
	 * 
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 60 * 60 * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * Method to encode given password for password validation
	 * 
	 * @param password
	 * @return
	 */
	private String encodePassword(String password) {
		String encryptedpassword = null;
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(password.getBytes());
			byte[] bytes = m.digest();

			StringBuilder s = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			encryptedpassword = s.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encryptedpassword;
	}

	private boolean validateOtp(String mfaSecret, String otp) {
		String mfaCode = MfaUtil.getTOTPCode(mfaSecret);
		boolean result = false;
		if(mfaCode.equalsIgnoreCase(otp))
			result = true;
		
		return result;
	}

}

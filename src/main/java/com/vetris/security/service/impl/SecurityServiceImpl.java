package com.vetris.security.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
import com.vetris.security.model.SignOnModel;
import com.vetris.security.model.User;
import com.vetris.security.repository.SecurityRepository;
import com.vetris.security.service.SecurittyService;

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

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.token.validity}")
	private int jwtTokenValidity;

	@Autowired
	SecurityRepository securityRepository;

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
	public ResponseEntity<?> signon(String auth)
			throws UnauthorizedException, InvalidUserException, TokenExpiredException {
		CommonResponseDTO resultDto = new CommonResponseDTO();
		HttpHeaders responseHeaders = new HttpHeaders();
		auth = auth.replace("Basic ", "");
		byte[] baseDecode = Base64.getDecoder().decode(auth);
		String[] userCred = new String(baseDecode).split(":");
		User resultUser = securityRepository.findByLoginId(userCred[0]);
		if (Objects.nonNull(resultUser)) {
			if (resultUser.getPassword().equals(encodePassword(userCred[1]))) {
				Map<String, Object> claims = new HashMap<>();
				claims.put(USERUUID, resultUser.getId());
				String jwtToken = doGenerateToken(claims, userCred[0]);
				responseHeaders.set(AUTHORIZATION, jwtToken);

				resultDto.setStatus(StatusType.SUCCESS.getMessage());
				resultDto.setPayload(persistDataToSignInModel(decodeToken(jwtToken)));
				resultDto.setMessage("User logged in successfully");

			} else {
				throw new UnauthorizedException(ExceptionCodes.UNAUTHORIZED_USER.getMessage());
			}
		} else {
			throw new InvalidUserException(ExceptionCodes.USER_NOT_FOUND.getMessage());
		}

		return ResponseEntity.ok().headers(responseHeaders).body(resultDto);
	}

	/**
	 * Method to decode the given JWT token using secret
	 * 
	 * @throws TokenExpiredException
	 */
	@Override
	public Claims decodeToken(String token) throws TokenExpiredException {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			;
		} catch (Exception e) {
			throw new TokenExpiredException(ExceptionCodes.TOKEN_EXPIRED.getMessage());
		}
		return claims;
	}

	/**
	 * method is to convert claims to SignInModel
	 * @param claims
	 * @return 
	 */
	private SignOnModel persistDataToSignInModel(Claims claims) {

		SignOnModel result = new SignOnModel();
		result.setLoginId(claims.getSubject());
		result.setUserId(claims.get(USERUUID).toString());
		result.setTokenIssuedDate(claims.getIssuedAt());
		result.setTokenExpiredDate(claims.getExpiration());

		return result;
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
}

/**
 * 
 */
package com.bourntec.vetrisSecurityServer.service.impl;

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

import com.bourntec.vetrisSecurityServer.exception.UnauthorizedException;
import com.bourntec.vetrisSecurityServer.model.Users;
import com.bourntec.vetrisSecurityServer.repository.VetrisSecurityServerRepository;
import com.bourntec.vetrisSecurityServer.service.VetrisSecurittyServerService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Donepudi Suresh
 *
 */

@Service
public class VetrisSecurityServerServiceImpl implements VetrisSecurittyServerService{

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("${jwt.secret}")
	private String secret;
	
	@Autowired
	VetrisSecurityServerRepository vetrisSecurityServerRepository;
	
	/**
	 *This method to login the user
	 *@param auth
	 */
	@Override
	public ResponseEntity<?> signon(String auth) throws UnauthorizedException {
		HttpHeaders responseHeaders = new HttpHeaders();
		String result =null;
		auth=auth.replace("Basic ", "");
		byte[] baseDecode=Base64.getDecoder().decode(auth);
		String[] userCred = new String(baseDecode).split(":");
		Users resultUser = vetrisSecurityServerRepository.findloginId(userCred[0]);
			if(Objects.nonNull(resultUser)) {
				if(resultUser.getPassword().equals(encodePassword(userCred[1]))) {
					Map<String, Object> claims = new HashMap<>();
					claims.put("id", resultUser.getId());
					String jwtToken= doGenerateToken(claims,userCred[0]);
					responseHeaders.set("Authorization", jwtToken);
					result = userCred[0]+" logged in";
				}else {
					throw new UnauthorizedException("User not Authorized");
				}
			}else {
				throw new UnauthorizedException("User not Found");
			}
		
		return ResponseEntity.ok().headers(responseHeaders).body(result);
	}
	
	/**
	 *This method to decode the given JWT token using secret
	 */
	@Override
	public Claims decodeToken(String token) throws UnauthorizedException{
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secret)
					.parseClaimsJws(token).getBody();
		}catch (Exception e) {
			throw new UnauthorizedException("Token expired");
		}
		return claims;
	}

	/**
	 * This method to create JWT token using given parameters
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	/**
	 * This method to encode given password for password validation
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

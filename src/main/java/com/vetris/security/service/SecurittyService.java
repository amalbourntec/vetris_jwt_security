package com.vetris.security.service;

import org.springframework.http.ResponseEntity;

import com.vetris.security.dto.CommonResponseDTO;
import com.vetris.security.exception.InvalidUserException;
import com.vetris.security.exception.TokenExpiredException;
import com.vetris.security.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;

/**
 * @author Donepudi Suresh
 *
 */
public interface SecurittyService {
	
	/**
	 * Method to login using username and password
	 * @param auth
	 * @return
	 * @throws UnauthorizedException
	 * @throws InvalidUserException
	 * @throws TokenExpiredException
	 */
	public ResponseEntity<CommonResponseDTO> signon(String auth) throws UnauthorizedException, InvalidUserException, TokenExpiredException;
	
	/**
	 *Method to decode the given JWT token using secret
	 * @throws TokenExpiredException 
	 */
	public Claims decodeToken(String token) throws TokenExpiredException;
}

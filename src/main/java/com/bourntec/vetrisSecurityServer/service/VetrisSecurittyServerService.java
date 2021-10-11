package com.bourntec.vetrisSecurityServer.service;

import org.springframework.http.ResponseEntity;

import com.bourntec.vetrisSecurityServer.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;

/**
 * @author Donepudi Suresh
 *
 */
public interface VetrisSecurittyServerService {
	
	/**
	 *This method to login the user
	 *@param auth
	 */
	public ResponseEntity<?> signon(String auth) throws UnauthorizedException;
	
	/**
	 *This method to decode the given JWT token using secret
	 */
	public Claims decodeToken(String token) throws UnauthorizedException;
}

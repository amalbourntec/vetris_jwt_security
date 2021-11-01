package com.vetris.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vetris.security.dto.CommonResponseDTO;
import com.vetris.security.exception.InvalidUserException;
import com.vetris.security.exception.TokenExpiredException;
import com.vetris.security.exception.UnauthorizedException;
import com.vetris.security.service.SecurittyService;

import io.jsonwebtoken.Claims;


/**
 * @author Donepudi Suresh
 *
 */
@RestController
@RequestMapping("v1/users")
@CrossOrigin(origins = "*")
public class SecurityController {
	
	@Autowired
	private SecurittyService securittyService;
	
	
	/**
	 * Method to login using username and password
	 * @param auth
	 * @return
	 * @throws UnauthorizedException
	 * @throws InvalidUserException
	 * @throws TokenExpiredException
	 */
	@PostMapping("/signon")
	public ResponseEntity<CommonResponseDTO> signon(@RequestHeader("Authorization") String auth) throws UnauthorizedException,InvalidUserException, TokenExpiredException {
		return securittyService.signon(auth);
		
	}
	
	/**
	 *Method to decode the given JWT token using secret
	 * @throws UnauthorizedException 
	 * @throws TokenExpiredException 
	 */
	@GetMapping("/decode")
	public Claims decodeToken(@RequestHeader("AUTHORIZATION") String token) throws TokenExpiredException {
		return securittyService.decodeToken(token);
		
	}
	
}

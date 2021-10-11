package com.bourntec.vetrisSecurityServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bourntec.vetrisSecurityServer.exception.UnauthorizedException;
import com.bourntec.vetrisSecurityServer.service.VetrisSecurittyServerService;

import io.jsonwebtoken.Claims;

/**
 * @author Donepudi Suresh
 *
 */
@RestController
@RequestMapping("v1/users")
@CrossOrigin(origins = "*")
public class VetrisSecurityServerController {
	
	@Autowired
	VetrisSecurittyServerService vetrisSecurittyServerService;
	
	/**
	 *This method to login the user
	 *@param auth
	 */
	@PostMapping("/signon")
	public ResponseEntity<?> signon(@RequestHeader("Authorization") String auth) throws UnauthorizedException{
		return vetrisSecurittyServerService.signon(auth);
		
	}
	
	/**
	 *This method to decode the given JWT token using secret
	 * @throws UnauthorizedException 
	 */
	@GetMapping("/decode")
	public Claims decodeToken(@RequestHeader("AUTHORIZATION") String token) throws UnauthorizedException {
		return vetrisSecurittyServerService.decodeToken(token);
		
	}
	
}

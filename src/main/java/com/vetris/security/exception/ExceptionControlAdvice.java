/**
 * 
 */
package com.vetris.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Donepudi Suresh
 *
 */
@ControllerAdvice
public class ExceptionControlAdvice {

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException ex){
		ExceptionResponse response = new ExceptionResponse();
		
		response.setResponseMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.UNAUTHORIZED);
		
		return new ResponseEntity<>(response, response.getHttpStatus());
	}
	
	@ExceptionHandler(InvalidUserException.class)
	public ResponseEntity<?> handleInvalidUserException(InvalidUserException ex){
		ExceptionResponse response = new ExceptionResponse();
		
		response.setResponseMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(response, response.getHttpStatus());
	}
	
	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<?> handleTokenExpiredException(TokenExpiredException ex){
		ExceptionResponse response = new ExceptionResponse();
		
		response.setResponseMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(response, response.getHttpStatus());
	}
	
	
}

/**
 * 
 */
package com.vetris.security.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ExceptionResponse> handleThrowable(Throwable ex,HttpServletRequest request){
		ExceptionResponse response = new ExceptionResponse();
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setMessage(ex.getMessage());
		response.setTimestamp(new Date());
		response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setPath(request.getRequestURI());
		
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException ex,HttpServletRequest request){
		ExceptionResponse response = new ExceptionResponse();
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setMessage(ex.getMessage());
		response.setTimestamp(new Date());
		response.setHttpStatus(HttpStatus.UNAUTHORIZED);
		response.setPath(request.getRequestURI());
		
		return new ResponseEntity<>(response, response.getHttpStatus());
	}
	
	@ExceptionHandler(InvalidUserException.class)
	public ResponseEntity<ExceptionResponse> handleInvalidUserException(InvalidUserException ex,HttpServletRequest request){
		ExceptionResponse response = new ExceptionResponse();
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setMessage(ex.getMessage());
		response.setTimestamp(new Date());
		response.setHttpStatus(HttpStatus.UNAUTHORIZED);
		response.setPath(request.getRequestURI());
		
		return new ResponseEntity<>(response, response.getHttpStatus());
	}
	
	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<ExceptionResponse> handleTokenExpiredException(TokenExpiredException ex,HttpServletRequest request){
		ExceptionResponse response = new ExceptionResponse();
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setMessage(ex.getMessage());
		response.setTimestamp(new Date());
		response.setHttpStatus(HttpStatus.UNAUTHORIZED);
		response.setPath(request.getRequestURI());
		
		return new ResponseEntity<>(response, response.getHttpStatus());
	}
	
	
}

/**
 * 
 */
package com.vetris.security.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Donepudi Suresh
 *
 */
@Setter
@Getter
public class ExceptionResponse {
	private String responseMessage;
	
	private HttpStatus httpStatus;

}

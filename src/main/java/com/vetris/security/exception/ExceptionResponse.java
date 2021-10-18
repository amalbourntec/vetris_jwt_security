/**
 * 
 */
package com.vetris.security.exception;

import java.util.Date;

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
	private Date timestamp;
	private int status;
	private HttpStatus httpStatus;
	private String message;
	private String path;

}

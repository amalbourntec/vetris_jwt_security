package com.vetris.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Donepudi Suresh
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class InvalidUserException extends Exception{


	private static final long serialVersionUID = -402504043461198027L;
	
	private String message;
}
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
public class TokenExpiredException extends Exception{

	private static final long serialVersionUID = 6942751272542673284L;

	private final String message;
}
package com.vetris.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Donepudi Suresh
 *
 */
@AllArgsConstructor
@Getter
public enum ExceptionCodes {
	UNAUTHORIZED_USER("Unauthorized User."),
	USER_NOT_FOUND("Unable to fatch the User."),
	INVALID_USER("Invalid user"),
	TOKEN_EXPIRED("Token expired.");
	private String message;	
}

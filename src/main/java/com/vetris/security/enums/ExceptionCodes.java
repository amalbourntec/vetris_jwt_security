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
	INVALID_OTP("Invalid OTP"),
	TOKEN_EXPIRED("Token expired."),
	MFA_NOT_VALIDATED("Unable to validate MFA token");
	
	private String message;	
}

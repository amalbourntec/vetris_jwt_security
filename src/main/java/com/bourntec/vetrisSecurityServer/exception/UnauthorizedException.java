package com.bourntec.vetrisSecurityServer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UnauthorizedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1259785730170985647L;
	
	private String message;
}

package com.vetris.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusType {
	SUCCESS("Success"),
	FAILURE("Failure");
	
	private String message;

}

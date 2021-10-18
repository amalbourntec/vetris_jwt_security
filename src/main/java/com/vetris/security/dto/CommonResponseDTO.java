package com.vetris.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponseDTO {

	private String status;
	private Object payload;
	private String message;
}

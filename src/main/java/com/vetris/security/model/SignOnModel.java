package com.vetris.security.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Donepudi Suresh
 *
 */
@Setter
@Getter
public class SignOnModel {
	private String loginId;
	private String userId;
	private Date tokenIssuedDate;
	private Date tokenExpiredDate;

}

package com.vetris.security.utils;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import de.taimos.totp.TOTP;

/**
 * @author Donepudi Suresh
 *
 */
public class MfaUtil {
	
	private MfaUtil() {
		super();
	}

	/**
	 * * This method get current otp form secret key
	 * converts base32 encoded secret keys to hex 
	 * and 
	 * uses the TOTP to turn them into 6-digits codes based on the current time
	 * @param secretKey
	 * @return
	 */
	public static String getTOTPCode(String secretKey) {
	    Base32 base32 = new Base32();
	    byte[] bytes = base32.decode(secretKey);
	    String hexKey = Hex.encodeHexString(bytes);
	    return TOTP.getOTP(hexKey);
	}

}

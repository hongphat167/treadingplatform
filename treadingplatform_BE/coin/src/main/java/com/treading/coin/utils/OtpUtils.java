package com.treading.coin.utils;


import java.util.Random;

/**
 * The type Otp utils.
 */
public class OtpUtils {

	/**
	 * Generate otp string.
	 *
	 * @return the string
	 */
	public static String generateOTP() {
		int otpLength = 6;
		Random random = new Random();

		StringBuilder otp = new StringBuilder();
		for (int i = 0; i < otpLength; i++) {
			otp.append(random.nextInt(10));
		}
		return otp.toString();
	}
}

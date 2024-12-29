package com.treading.coin.controller.request;

import lombok.Data;

/**
 * The type Reset password request.
 */
@Data
public class ResetPasswordRequest {

	private String otp;
	private String password;
}

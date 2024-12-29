package com.treading.coin.controller.response;

import lombok.Data;

/**
 * The type Auth response.
 */
@Data
public class AuthResponse {

	private String jwt;
	private boolean status;
	private String message;
	private boolean isTwoFactorAuthEnabled;
	private String session;
}

package com.treading.coin.controller.request;

import com.treading.coin.enums.VerificationType;
import lombok.Data;

/**
 * The type Forgot password token request.
 */
@Data
public class ForgotPasswordTokenRequest {

	private String sendTo;
	private VerificationType verificationType;

}

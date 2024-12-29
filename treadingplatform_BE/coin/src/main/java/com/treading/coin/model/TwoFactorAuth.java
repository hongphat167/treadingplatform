package com.treading.coin.model;

import com.treading.coin.enums.VerificationType;
import lombok.Data;

/**
 * The type Two factor auth.
 */
@Data
public class TwoFactorAuth {

	private boolean isEnabled = false;
	private VerificationType sendTo;
}

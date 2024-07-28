package com.treading.coin.model;

import com.treading.coin.enums.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {

  /**
   * is_enabled
   */
  private boolean isEnabled = false;
  /**
   * verification_type
   */
  private VerificationType sendTo;
}

package com.treading.coin.controller.request;

import com.treading.coin.enums.VerificationType;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {

  /**
   * send_to
   */
  private String sendTo;
  /**
   * verification_type
   */
  private VerificationType verificationType;

}

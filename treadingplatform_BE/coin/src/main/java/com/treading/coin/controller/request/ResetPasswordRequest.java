package com.treading.coin.controller.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {

  /**
   * otp
   */
  private String otp;
  /**
   * password
   */
  private String password;
}

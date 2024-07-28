package com.treading.coin.controller.response;

import lombok.Data;

@Data
public class AuthResponse {

  /**
   * jwt
   */
  private String jwt;
  /**
   * status
   */
  private boolean status;
  /**
   * message
   */
  private String message;
  /**
   * is_two_factor_auth_enabled
   */
  private boolean isTwoFactorAuthEnabled;
  /**
   * session
   */
  private String session;
}

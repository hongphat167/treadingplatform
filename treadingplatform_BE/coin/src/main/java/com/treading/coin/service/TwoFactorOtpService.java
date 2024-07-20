package com.treading.coin.service;

import com.treading.coin.model.TwoFactorOTP;
import com.treading.coin.model.User;

public interface TwoFactorOtpService {

  /**
   * Create Two Factor Otp
   *
   * @param user user
   * @param otp  otp
   * @param jwt  jwt
   * @return TwoFactorOTP
   */
  TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);

  /**
   * Find By User
   *
   * @param userId userId
   * @return TwoFactorOTP
   */
  TwoFactorOTP findByUser(Long userId);

  /**
   * Find By Id
   *
   * @param id id
   * @return TwoFactorOTP
   */
  TwoFactorOTP findById(String id);

  /**
   * Verify Two Factor Otp
   *
   * @param twoFactorOTP twoFactorOTP
   * @param otp          otp
   * @return true/false
   */
  boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp);

  /**
   * Delete Two Factor Otp
   *
   * @param twoFactorOTP twoFactorOTP
   */
  void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);
}

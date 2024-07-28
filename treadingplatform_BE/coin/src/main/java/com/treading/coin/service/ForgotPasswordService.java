package com.treading.coin.service;

import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.ForgotPasswordToken;
import com.treading.coin.model.User;

public interface ForgotPasswordService {

  /**
   * Create Token
   *
   * @param user             user
   * @param id               id
   * @param otp              otp
   * @param verificationType verificationType
   * @param sendTo           sendTo
   * @return ForgotPasswordToken
   */
  ForgotPasswordToken createToken(User user, String id, String otp,
      VerificationType verificationType, String sendTo);

  /**
   * Find By Id
   *
   * @param id id
   * @return ForgotPasswordToken
   */
  ForgotPasswordToken findById(String id);

  /**
   * Find By User
   *
   * @param userId userId
   * @return ForgotPasswordToken
   */
  ForgotPasswordToken findByUser(Long userId);

  /**
   * Delete Token
   *
   * @param forgotPasswordToken forgotPasswordToken
   */
  void deleteToken(ForgotPasswordToken forgotPasswordToken);
}

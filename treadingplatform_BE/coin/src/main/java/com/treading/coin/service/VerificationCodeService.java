package com.treading.coin.service;

import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.User;
import com.treading.coin.model.VerificationCode;

public interface VerificationCodeService {

  /**
   * Send Verification Code
   *
   * @param user             user
   * @param verificationType verificationType
   * @return VerificationCode
   */
  VerificationCode sendVerificationCode(User user, VerificationType verificationType);

  /**
   * Get Verification Code By Id
   *
   * @param id id
   * @return VerificationCode
   */
  VerificationCode getVerificationCodeById(Long id);

  /**
   * Get Verification Code By User
   *
   * @param userId userId
   * @return VerificationCode
   */
  VerificationCode getVerificationCodeByUser(Long userId);

  /**
   * Delete Verification Code By Id
   *
   * @param verificationCode verificationCode
   */
  void deleteVerificationCodeById(VerificationCode verificationCode);
}

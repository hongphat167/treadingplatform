package com.treading.coin.service.impl;

import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.User;
import com.treading.coin.model.VerificationCode;
import com.treading.coin.repository.VerificationCodeRepository;
import com.treading.coin.service.VerificationCodeService;
import com.treading.coin.utils.OtpUtils;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

  @Autowired
  private VerificationCodeRepository verificationCodeRepository;

  /**
   * Send Verification Code
   *
   * @param user             user
   * @param verificationType verificationType
   * @return verificationCode1
   */
  @Override
  public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {

    VerificationCode verificationCode1 = new VerificationCode();
    verificationCode1.setOtp(OtpUtils.generateOTP());
    verificationCode1.setVerificationType(verificationType);
    verificationCode1.setUser(user);

    return verificationCodeRepository.save(verificationCode1);
  }

  /**
   * Get Verification Code By Id
   *
   * @param id id
   * @return verificationCode
   */
  @Override
  public VerificationCode getVerificationCodeById(Long id) {

    Optional<VerificationCode> verificationCode = Optional.of(
        verificationCodeRepository.findById(id).orElseThrow());

    return verificationCode.get();
  }

  /**
   * Get Verification Code By User
   *
   * @param userId userId
   * @return userId
   */
  @Override
  public VerificationCode getVerificationCodeByUser(Long userId) {

    return verificationCodeRepository.findByUserId(userId);
  }

  /**
   * Delete Verification Code By Id
   *
   * @param verificationCode verificationCode
   */
  @Override
  public void deleteVerificationCodeById(VerificationCode verificationCode) {

    verificationCodeRepository.delete(verificationCode);

  }
}

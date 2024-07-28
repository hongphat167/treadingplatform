package com.treading.coin.service.impl;

import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.ForgotPasswordToken;
import com.treading.coin.model.User;
import com.treading.coin.repository.ForgotPasswordRepository;
import com.treading.coin.service.ForgotPasswordService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

  @Autowired
  private ForgotPasswordRepository forgotPasswordRepository;

  /**
   * Create Token
   *
   * @param user             user
   * @param id               id
   * @param otp              otp
   * @param verificationType verificationType
   * @param sendTo           sendTo
   * @return token
   */
  @Override
  public ForgotPasswordToken createToken(User user, String id, String otp,
      VerificationType verificationType, String sendTo) {

    ForgotPasswordToken token = new ForgotPasswordToken();
    token.setUser(user);
    token.setSendTo(sendTo);
    token.setVerificationType(verificationType);
    token.setOtp(otp);
    token.setId(id);
    return forgotPasswordRepository.save(token);
  }

  /**
   * Find By Id
   *
   * @param id id
   * @return token
   */
  @Override
  public ForgotPasswordToken findById(String id) {

    Optional<ForgotPasswordToken> token = forgotPasswordRepository.findById(id);
    return token.orElse(null);
  }

  /**
   * Find By User
   *
   * @param userId userId
   * @return userId
   */
  @Override
  public ForgotPasswordToken findByUser(Long userId) {
    return forgotPasswordRepository.findByUserId(userId);
  }

  /**
   * Delete Token
   *
   * @param forgotPasswordToken forgotPasswordToken
   */
  @Override
  public void deleteToken(ForgotPasswordToken forgotPasswordToken) {
    forgotPasswordRepository.delete(forgotPasswordToken);
  }
}

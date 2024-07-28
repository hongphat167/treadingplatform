package com.treading.coin.service.impl;

import com.treading.coin.model.TwoFactorOTP;
import com.treading.coin.model.User;
import com.treading.coin.repository.TwoFactorOtpRepository;
import com.treading.coin.service.TwoFactorOtpService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService {

  @Autowired
  private TwoFactorOtpRepository twoFactorOtpRepository;

  /**
   * Create Two Factor Otp
   *
   * @param user user
   * @param otp  otp
   * @param jwt  jwt
   * @return twoFactorOTP
   */
  @Override
  public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
    UUID uuid = UUID.randomUUID();

    String id = uuid.toString();

    TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
    twoFactorOTP.setOtp(otp);
    twoFactorOTP.setJwt(jwt);
    twoFactorOTP.setId(id);
    twoFactorOTP.setUser(user);

    return twoFactorOtpRepository.save(twoFactorOTP);

  }

  /**
   * Find By User
   *
   * @param userId userId
   * @return userId
   */
  @Override
  public TwoFactorOTP findByUser(Long userId) {
    return twoFactorOtpRepository.findByUserId(userId);
  }

  /**
   * Find By Id
   *
   * @param id id
   * @return otp
   */
  @Override
  public TwoFactorOTP findById(String id) {
    Optional<TwoFactorOTP> otp = twoFactorOtpRepository.findById(id);
    return otp.orElse(null);
  }

  /**
   * Verify Two Factor Otp
   *
   * @param twoFactorOTP twoFactorOTP
   * @param otp          otp
   * @return otp
   */
  @Override
  public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp) {
    return twoFactorOTP.getOtp().equals(otp);
  }

  /**
   * Delete Two Factor Otp
   *
   * @param twoFactorOTP twoFactorOTP
   */
  @Override
  public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP) {
    twoFactorOtpRepository.delete(twoFactorOTP);
  }
}

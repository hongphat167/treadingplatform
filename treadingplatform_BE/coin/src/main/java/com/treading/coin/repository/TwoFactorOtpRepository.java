package com.treading.coin.repository;

import com.treading.coin.model.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {

  /**
   * Find By UserId
   *
   * @param userId userId
   * @return TwoFactorOTP
   */
  TwoFactorOTP findByUserId(Long userId);
}

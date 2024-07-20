package com.treading.coin.repository;

import com.treading.coin.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

  /**
   * Find By UserId
   *
   * @param userId userId
   * @return VerificationCode
   */
  public VerificationCode findByUserId(Long userId);

}

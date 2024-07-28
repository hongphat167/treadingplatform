package com.treading.coin.repository;

import com.treading.coin.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, String> {

  /**
   * Find By User Id
   *
   * @param userId userId
   * @return ForgotPasswordToken
   */
  ForgotPasswordToken findByUserId(Long userId);
}

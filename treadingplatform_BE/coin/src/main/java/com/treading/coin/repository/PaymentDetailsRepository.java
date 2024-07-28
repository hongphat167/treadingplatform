package com.treading.coin.repository;

import com.treading.coin.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {

  /**
   * Find By User Id
   *
   * @param userId userId
   * @return PaymentDetails
   */
  PaymentDetails findByUserId(Long userId);
}

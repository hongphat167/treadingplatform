package com.treading.coin.service;

import com.treading.coin.model.PaymentDetails;
import com.treading.coin.model.User;

public interface PaymentDetailsService {

  /**
   * Add Payment Details
   *
   * @param accountNumber accountNumber
   * @param accountHolderName accountHolderName
   * @param ifsc ifsc
   * @param bankName bankName
   * @param user user
   * @return PaymentDetails
   */
  PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc,
      String bankName, User user);

  /**
   * Get Users Payment Details
   *
   * @param user user
   * @return PaymentDetails
   */
  PaymentDetails getUsersPaymentDetails(User user);
}

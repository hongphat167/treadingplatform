package com.treading.coin.service.impl;

import com.treading.coin.model.PaymentDetails;
import com.treading.coin.model.User;
import com.treading.coin.repository.PaymentDetailsRepository;
import com.treading.coin.service.PaymentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

  @Autowired
  private PaymentDetailsRepository paymentDetailsRepository;

  /**
   * Add Payment Details
   *
   * @param accountNumber     accountNumber
   * @param accountHolderName accountHolderName
   * @param ifsc              ifsc
   * @param bankName          bankName
   * @param user              user
   * @return PaymentDetails
   */
  @Override
  public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName,
      String ifsc, String bankName, User user) {
    PaymentDetails paymentDetails = new PaymentDetails();
    paymentDetails.setAccountNumber(accountNumber);
    paymentDetails.setAccountHolderName(accountHolderName);
    paymentDetails.setIfsc(ifsc);
    paymentDetails.setBankName(bankName);
    paymentDetails.setUser(user);
    return paymentDetailsRepository.save(paymentDetails);
  }

  /**
   * Get Users Payment Details
   *
   * @param user user
   * @return PaymentDetails
   */
  @Override
  public PaymentDetails getUsersPaymentDetails(User user) {
    return paymentDetailsRepository.findByUserId(user.getId());
  }
}

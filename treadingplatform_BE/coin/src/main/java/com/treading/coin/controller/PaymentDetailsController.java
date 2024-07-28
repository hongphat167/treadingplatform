package com.treading.coin.controller;

import com.treading.coin.model.PaymentDetails;
import com.treading.coin.model.User;
import com.treading.coin.service.PaymentDetailsService;
import com.treading.coin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment-details")
public class PaymentDetailsController {

  @Autowired
  private PaymentDetailsService paymentDetailsService;

  @Autowired
  private UserService userService;

  /**
   * /api/payment-details/add-payment
   */
  @PostMapping("/add-payment")
  public ResponseEntity<PaymentDetails> addPaymentDetails(
      @RequestHeader("Authorization") String jwt, @RequestBody PaymentDetails request)
      throws Exception {
    User user = userService.findUserProfileByJwt(jwt);

    PaymentDetails paymentDetails = paymentDetailsService.addPaymentDetails(
        request.getAccountNumber(), request.getAccountHolderName(),
        request.getIfsc(), request.getBankName(), user);

    return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);
  }

  /**
   * /api/payment-details/get-payment
   */
  @GetMapping("/get-payment")
  public ResponseEntity<PaymentDetails> getUsersPaymentDetails(
      @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserProfileByJwt(jwt);
    PaymentDetails paymentDetails = paymentDetailsService.getUsersPaymentDetails(user);

    return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
  }
}

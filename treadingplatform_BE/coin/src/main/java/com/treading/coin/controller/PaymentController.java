package com.treading.coin.controller;

import com.treading.coin.controller.response.PaymentResponse;
import com.treading.coin.enums.PaymentMethod;
import com.treading.coin.model.PaymentOrder;
import com.treading.coin.model.User;
import com.treading.coin.service.PaymentService;
import com.treading.coin.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

  @Autowired
  private PaymentService paymentService;
  @Autowired
  private UserService userService;

  @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
  public ResponseEntity<PaymentResponse> paymentHandler(@PathVariable PaymentMethod paymentMethod,
      @PathVariable Long amount, @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserProfileByJwt(jwt);

    String baseUrl = "http://localhost:8081";

    PaymentResponse paymentResponse = new PaymentResponse();

    PaymentOrder order = paymentService.createPaymentOrder(user, amount, paymentMethod);

    if (paymentMethod.equals(PaymentMethod.ZALOPAY)) {

      paymentResponse.setPaymentUrl(
          paymentService.createVnPayPayment(user, amount, String.valueOf(order.getId()), baseUrl));
      paymentResponse.setCode("00");
      paymentResponse.setMessage("success");
      paymentResponse.setOrderId(String.valueOf(order.getId()));
    } else if (paymentMethod.equals(PaymentMethod.MOMO)) {
      // TO DO MOMO PAYMENT
      return null;
    }
    return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
  }

  @GetMapping("/vnpay-payment-return")
  public ResponseEntity<PaymentResponse> paymentReturn(
      HttpServletRequest request) throws Exception {

    PaymentOrder order = paymentService.getPaymentOrderById(
        Long.valueOf(request.getParameter("vnp_OrderInfo")));

    // Check payment success or not
    boolean paymentStatus = paymentService.orderReturn(order, request);

    PaymentResponse paymentResponse = new PaymentResponse();
    // Handle the payment result
    if (paymentStatus) {

      paymentResponse.setOrderId(request.getParameter("vnp_OrderInfo"));
      paymentResponse.setCode(request.getParameter("vnp_TransactionStatus"));
      return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    } else if (!paymentStatus) {

      paymentResponse.setOrderId(request.getParameter("vnp_OrderInfo"));
      paymentResponse.setCode(request.getParameter("vnp_TransactionStatus"));

      return new ResponseEntity<>(paymentResponse, HttpStatus.NOT_ACCEPTABLE);
    } else {
      throw new Exception("Invalid payment");
    }
  }
}

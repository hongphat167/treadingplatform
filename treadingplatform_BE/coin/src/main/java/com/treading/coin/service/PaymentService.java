package com.treading.coin.service;

import com.treading.coin.controller.response.PaymentResponse;
import com.treading.coin.enums.PaymentMethod;
import com.treading.coin.model.PaymentOrder;
import com.treading.coin.model.User;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface PaymentService {

  /**
   * Create Payment Order
   *
   * @param user          user
   * @param amount        amount
   * @param paymentMethod paymentMethod
   * @return PaymentOrder
   */
  PaymentOrder createPaymentOrder(User user, Long amount, PaymentMethod paymentMethod);

  /**
   * Get Payment Order By Id
   *
   * @param id id
   * @return PaymentOrder
   */
  PaymentOrder getPaymentOrderById(Long id) throws Exception;

  /**
   * Process Payment Order
   *
   * @param paymentOrder paymentOrder
   * @param request      request
   * @return Boolean
   */
  Boolean processPaymentOrder(PaymentOrder paymentOrder, HttpServletRequest request)
      throws UnsupportedEncodingException;

  /**
   * Create Zalo Pay Payment
   *
   * @param user   user
   * @param amount amount
   * @return PaymentResponse
   */
  PaymentResponse createZaloPayPayment(User user, Long amount);

  /**
   * Create Vn Pay Payment
   *
   * @param user    user
   * @param amount  amount
   * @param orderId orderId
   * @return PaymentResponse
   */
  String createVnPayPayment(User user, Long amount, String orderId,
      String urlReturn) throws Exception;

  boolean orderReturn(PaymentOrder paymentOrder, HttpServletRequest request)
      throws UnsupportedEncodingException;
}

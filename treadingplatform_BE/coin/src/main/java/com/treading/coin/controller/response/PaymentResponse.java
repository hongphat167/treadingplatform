package com.treading.coin.controller.response;

import lombok.Data;

@Data
public class PaymentResponse {
  /**
   * order_id
   */
  private String orderId;
  /**
   * code
   */
  private String code;
  /**
   * message
   */
  private String message;
  /**
   * payment_url
   */
  private String paymentUrl;
}

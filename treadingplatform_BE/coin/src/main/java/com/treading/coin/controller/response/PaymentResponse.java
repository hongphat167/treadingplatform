package com.treading.coin.controller.response;


import lombok.Data;

/**
 * The type Payment response.
 */
@Data
public class PaymentResponse {
	private String orderId;
	private String code;
	private String message;
	private String paymentUrl;
}

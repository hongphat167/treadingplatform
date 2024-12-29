package com.treading.coin.service;

import com.treading.coin.controller.response.PaymentResponse;
import com.treading.coin.enums.PaymentMethod;
import com.treading.coin.model.PaymentOrder;
import com.treading.coin.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

/**
 * The interface Payment service.
 */
public interface PaymentService {

	/**
	 * Create payment order payment order.
	 *
	 * @param user          the user
	 * @param amount        the amount
	 * @param paymentMethod the payment method
	 * @return the payment order
	 */
	PaymentOrder createPaymentOrder(User user, Long amount, PaymentMethod paymentMethod);

	/**
	 * Gets payment order by id.
	 *
	 * @param id the id
	 * @return the payment order by id
	 * @throws Exception the exception
	 */
	PaymentOrder getPaymentOrderById(Long id) throws Exception;

	/**
	 * Create momo pay payment payment response.
	 *
	 * @param user   the user
	 * @param amount the amount
	 * @return the payment response
	 */
	PaymentResponse createMomoPayPayment(User user, Long amount);

	/**
	 * Create vn pay payment string.
	 *
	 * @param user      the user
	 * @param amount    the amount
	 * @param orderId   the order id
	 * @param urlReturn the url return
	 * @return the string
	 * @throws Exception the exception
	 */
	String createVnPayPayment(User user, Long amount, String orderId,
	                          String urlReturn) throws Exception;

	/**
	 * Order return boolean.
	 *
	 * @param paymentOrder the payment order
	 * @param request      the request
	 * @return the boolean
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	boolean orderReturn(PaymentOrder paymentOrder, HttpServletRequest request)
			throws UnsupportedEncodingException;
}

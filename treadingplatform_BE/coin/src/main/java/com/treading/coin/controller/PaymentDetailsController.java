package com.treading.coin.controller;

import com.treading.coin.model.PaymentDetails;
import com.treading.coin.model.User;
import com.treading.coin.service.PaymentDetailsService;
import com.treading.coin.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Payment details controller.
 */
@RestController
@RequestMapping("/api/payment-details")
public class PaymentDetailsController {


	private final PaymentDetailsService paymentDetailsService;

	private final UserService userService;

	/**
	 * Instantiates a new Payment details controller.
	 *
	 * @param paymentDetailsService the payment details service
	 * @param userService           the user service
	 */
	public PaymentDetailsController(PaymentDetailsService paymentDetailsService,
	                                UserService userService) {
		this.paymentDetailsService = paymentDetailsService;
		this.userService = userService;
	}

	/**
	 * Add payment details response entity.
	 *
	 * @param jwt     the jwt
	 * @param request the request
	 * @return the response entity
	 * @throws Exception the exception
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
	 * Gets users payment details.
	 *
	 * @param jwt the jwt
	 * @return the users payment details
	 * @throws Exception the exception
	 */
	@GetMapping("/get-payment")
	public ResponseEntity<PaymentDetails> getUsersPaymentDetails(
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserProfileByJwt(jwt);
		PaymentDetails paymentDetails = paymentDetailsService.getUsersPaymentDetails(user);

		return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
	}
}

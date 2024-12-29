package com.treading.coin.service;

import com.treading.coin.model.PaymentDetails;
import com.treading.coin.model.User;

/**
 * The interface Payment details service.
 */
public interface PaymentDetailsService {

	/**
	 * Add payment details payment details.
	 *
	 * @param accountNumber     the account number
	 * @param accountHolderName the account holder name
	 * @param ifsc              the ifsc
	 * @param bankName          the bank name
	 * @param user              the user
	 * @return the payment details
	 */
	PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc,
	                                 String bankName, User user);

	/**
	 * Gets users payment details.
	 *
	 * @param user the user
	 * @return the users payment details
	 */
	PaymentDetails getUsersPaymentDetails(User user);
}

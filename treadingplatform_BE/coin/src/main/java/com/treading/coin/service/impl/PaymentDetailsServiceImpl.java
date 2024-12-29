package com.treading.coin.service.impl;

import com.treading.coin.model.PaymentDetails;
import com.treading.coin.model.User;
import com.treading.coin.repository.PaymentDetailsRepository;
import com.treading.coin.service.PaymentDetailsService;
import org.springframework.stereotype.Service;

/**
 * The type Payment details service.
 */
@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {


	private final PaymentDetailsRepository paymentDetailsRepository;

	/**
	 * Instantiates a new Payment details service.
	 *
	 * @param paymentDetailsRepository the payment details repository
	 */
	protected PaymentDetailsServiceImpl(PaymentDetailsRepository paymentDetailsRepository) {
		this.paymentDetailsRepository = paymentDetailsRepository;
	}

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

	@Override
	public PaymentDetails getUsersPaymentDetails(User user) {
		return paymentDetailsRepository.findByUserId(user.getId());
	}
}

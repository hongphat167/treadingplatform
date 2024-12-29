package com.treading.coin.repository;

import com.treading.coin.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Payment details repository.
 */
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {

	/**
	 * Find by user id payment details.
	 *
	 * @param userId the user id
	 * @return the payment details
	 */
	PaymentDetails findByUserId(Long userId);
}

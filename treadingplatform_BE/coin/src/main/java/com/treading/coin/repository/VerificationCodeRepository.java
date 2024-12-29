package com.treading.coin.repository;

import com.treading.coin.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Verification code repository.
 */
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

	/**
	 * Find by user id verification code.
	 *
	 * @param userId the user id
	 * @return the verification code
	 */
	VerificationCode findByUserId(Long userId);

}

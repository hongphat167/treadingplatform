package com.treading.coin.repository;

import com.treading.coin.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Forgot password repository.
 */
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, String> {

	/**
	 * Find by user id forgot password token.
	 *
	 * @param userId the user id
	 * @return the forgot password token
	 */
	ForgotPasswordToken findByUserId(Long userId);
}

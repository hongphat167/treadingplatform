package com.treading.coin.repository;

import com.treading.coin.model.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Two factor otp repository.
 */
public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {

	/**
	 * Find by user id two factor otp.
	 *
	 * @param userId the user id
	 * @return the two factor otp
	 */
	TwoFactorOTP findByUserId(Long userId);
}

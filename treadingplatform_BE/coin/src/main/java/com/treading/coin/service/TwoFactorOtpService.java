package com.treading.coin.service;

import com.treading.coin.model.TwoFactorOTP;
import com.treading.coin.model.User;

/**
 * The interface Two factor otp service.
 */
public interface TwoFactorOtpService {

	/**
	 * Create two factor otp two factor otp.
	 *
	 * @param user the user
	 * @param otp  the otp
	 * @param jwt  the jwt
	 * @return the two factor otp
	 */
	TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);

	/**
	 * Find by user two factor otp.
	 *
	 * @param userId the user id
	 * @return the two factor otp
	 */
	TwoFactorOTP findByUser(Long userId);

	/**
	 * Find by id two factor otp.
	 *
	 * @param id the id
	 * @return the two factor otp
	 */
	TwoFactorOTP findById(String id);

	/**
	 * Verify two factor otp boolean.
	 *
	 * @param twoFactorOTP the two factor otp
	 * @param otp          the otp
	 * @return the boolean
	 */
	boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp);

	/**
	 * Delete two factor otp.
	 *
	 * @param twoFactorOTP the two factor otp
	 */
	void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);
}

package com.treading.coin.service;

import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.ForgotPasswordToken;
import com.treading.coin.model.User;

/**
 * The interface Forgot password service.
 */
public interface ForgotPasswordService {

	/**
	 * Create token forgot password token.
	 *
	 * @param user             the user
	 * @param id               the id
	 * @param otp              the otp
	 * @param verificationType the verification type
	 * @param sendTo           the send to
	 * @return the forgot password token
	 */
	ForgotPasswordToken createToken(User user, String id, String otp,
	                                VerificationType verificationType, String sendTo);

	/**
	 * Find by id forgot password token.
	 *
	 * @param id the id
	 * @return the forgot password token
	 */
	ForgotPasswordToken findById(String id);

	/**
	 * Find by user forgot password token.
	 *
	 * @param userId the user id
	 * @return the forgot password token
	 */
	ForgotPasswordToken findByUser(Long userId);

	/**
	 * Delete token.
	 *
	 * @param forgotPasswordToken the forgot password token
	 */
	void deleteToken(ForgotPasswordToken forgotPasswordToken);
}

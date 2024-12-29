package com.treading.coin.service;

import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.User;
import com.treading.coin.model.VerificationCode;

/**
 * The interface Verification code service.
 */
public interface VerificationCodeService {

	/**
	 * Send verification code verification code.
	 *
	 * @param user             the user
	 * @param verificationType the verification type
	 * @return the verification code
	 */
	VerificationCode sendVerificationCode(User user, VerificationType verificationType);

	/**
	 * Gets verification code by id.
	 *
	 * @param id the id
	 * @return the verification code by id
	 */
	VerificationCode getVerificationCodeById(Long id);

	/**
	 * Gets verification code by user.
	 *
	 * @param userId the user id
	 * @return the verification code by user
	 */
	VerificationCode getVerificationCodeByUser(Long userId);

	/**
	 * Delete verification code by id.
	 *
	 * @param verificationCode the verification code
	 */
	void deleteVerificationCodeById(VerificationCode verificationCode);
}

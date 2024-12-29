package com.treading.coin.service;

import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.User;


/**
 * The interface User service.
 */
public interface UserService {

	/**
	 * Find user profile by jwt user.
	 *
	 * @param jwt the jwt
	 * @return the user
	 * @throws Exception the exception
	 */
	User findUserProfileByJwt(String jwt) throws Exception;

	/**
	 * Find user by email user.
	 *
	 * @param email the email
	 * @return the user
	 * @throws Exception the exception
	 */
	User findUserByEmail(String email) throws Exception;

	/**
	 * Find user by user id user.
	 *
	 * @param userId the user id
	 * @return the user
	 * @throws Exception the exception
	 */
	User findUserByUserId(Long userId) throws Exception;

	/**
	 * Enable two factor authentication user.
	 *
	 * @param verificationType the verification type
	 * @param sendTo           the send to
	 * @param user             the user
	 * @return the user
	 */
	User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo,
	                                   User user);

	/**
	 * Update password.
	 *
	 * @param user        the user
	 * @param newPassword the new password
	 */
	void updatePassword(User user, String newPassword);
}

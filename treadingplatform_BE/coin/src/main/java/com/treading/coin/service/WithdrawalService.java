package com.treading.coin.service;

import com.treading.coin.model.User;
import com.treading.coin.model.Withdrawal;

import java.util.List;

/**
 * The interface Withdrawal service.
 */
public interface WithdrawalService {
	/**
	 * Request withdrawal withdrawal.
	 *
	 * @param amount the amount
	 * @param user   the user
	 * @return the withdrawal
	 */
	Withdrawal requestWithdrawal(Long amount, User user);

	/**
	 * Process withdrawal withdrawal.
	 *
	 * @param withdrawalId the withdrawal id
	 * @param accept       the accept
	 * @return the withdrawal
	 * @throws Exception the exception
	 */
	Withdrawal processWithdrawal(Long withdrawalId, boolean accept) throws Exception;

	/**
	 * Gets users withdrawal history.
	 *
	 * @param user the user
	 * @return the users withdrawal history
	 */
	List<Withdrawal> getUsersWithdrawalHistory(User user);

	/**
	 * Gets all withdrawal request.
	 *
	 * @return the all withdrawal request
	 */
	List<Withdrawal> getAllWithdrawalRequest();
}

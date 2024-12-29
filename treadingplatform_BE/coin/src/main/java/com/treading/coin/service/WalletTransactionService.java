package com.treading.coin.service;

import com.treading.coin.enums.WalletTransactionType;
import com.treading.coin.model.Wallet;
import com.treading.coin.model.WalletTransaction;

/**
 * The interface Wallet transaction service.
 */
public interface WalletTransactionService {

	/**
	 * Create transaction wallet transaction.
	 *
	 * @param wallet                the wallet
	 * @param walletTransactionType the wallet transaction type
	 * @param transferId            the transfer id
	 * @param purpose               the purpose
	 * @param amount                the amount
	 */
	void createTransaction(Wallet wallet,
	                       WalletTransactionType walletTransactionType,
	                       String transferId,
	                       String purpose,
	                       Long amount);

	/**
	 * Gets transaction.
	 *
	 * @param wallet the wallet
	 * @return the transaction
	 * @throws Exception the exception
	 */
	WalletTransaction getTransaction(Wallet wallet) throws Exception;
}

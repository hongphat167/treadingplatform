package com.treading.coin.service;

import com.treading.coin.model.Order;
import com.treading.coin.model.User;
import com.treading.coin.model.Wallet;

/**
 * The interface Wallet service.
 */
public interface WalletService {

	/**
	 * Gets user wallet.
	 *
	 * @param user the user
	 * @return the user wallet
	 */
	Wallet getUserWallet(User user);

	/**
	 * Add balance wallet.
	 *
	 * @param wallet the wallet
	 * @param money  the money
	 * @return the wallet
	 */
	Wallet addBalance(Wallet wallet, Long money);

	/**
	 * Find wallet by id wallet.
	 *
	 * @param id the id
	 * @return the wallet
	 */
	Wallet findWalletById(Long id);

	/**
	 * Wallet to wallet transfer wallet.
	 *
	 * @param sender         the sender
	 * @param receiverWallet the receiver wallet
	 * @param amount         the amount
	 * @return the wallet
	 * @throws Exception the exception
	 */
	Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;

	/**
	 * Pay order payment wallet.
	 *
	 * @param order the order
	 * @param user  the user
	 * @return the wallet
	 * @throws Exception the exception
	 */
	Wallet payOrderPayment(Order order, User user) throws Exception;

}

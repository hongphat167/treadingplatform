package com.treading.coin.repository;

import com.treading.coin.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Wallet transaction repository.
 */
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

	/**
	 * Find by wallet id wallet transaction.
	 *
	 * @param walletId the wallet id
	 * @return the wallet transaction
	 */
	WalletTransaction findByWalletId(Long walletId);
}

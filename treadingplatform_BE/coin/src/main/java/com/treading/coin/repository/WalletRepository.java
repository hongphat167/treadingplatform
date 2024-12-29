package com.treading.coin.repository;

import com.treading.coin.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Wallet repository.
 */
public interface WalletRepository extends JpaRepository<Wallet, Long> {

	/**
	 * Find by user id wallet.
	 *
	 * @param userId the user id
	 * @return the wallet
	 */
	Wallet findByUserId(Long userId);

}

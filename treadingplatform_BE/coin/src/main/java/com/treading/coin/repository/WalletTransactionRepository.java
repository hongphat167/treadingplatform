package com.treading.coin.repository;

import com.treading.coin.model.Wallet;
import com.treading.coin.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

  /**
   * Find By Wallet Id
   *
   * @param walletId walletId
   * @return WalletTransaction
   */
  WalletTransaction findByWalletId(Long walletId);
}

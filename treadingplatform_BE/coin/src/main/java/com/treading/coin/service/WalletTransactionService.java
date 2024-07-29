package com.treading.coin.service;

import com.treading.coin.enums.WalletTransactionType;
import com.treading.coin.model.Wallet;
import com.treading.coin.model.WalletTransaction;

public interface WalletTransactionService {

  /**
   * Create Transaction
   *
   * @param wallet                wallet
   * @param walletTransactionType walletTransactionType
   * @param transferId            transferId
   * @param purpose               purpose
   * @param amount                amount
   * @return WalletTransaction
   */
  WalletTransaction createTransaction(Wallet wallet, WalletTransactionType walletTransactionType,
      String transferId, String purpose, Long amount);

  /**
   * Get Transaction
   *
   * @param wallet wallet
   * @return WalletTransaction
   * @throws Exception
   */
  WalletTransaction getTransaction(Wallet wallet) throws Exception;
}

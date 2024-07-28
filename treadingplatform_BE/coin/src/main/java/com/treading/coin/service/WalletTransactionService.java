package com.treading.coin.service;

import com.treading.coin.enums.WalletTransactionType;
import com.treading.coin.model.Wallet;
import com.treading.coin.model.WalletTransaction;

public interface WalletTransactionService {

  WalletTransaction createTransaction(Wallet wallet, WalletTransactionType walletTransactionType,
      String transferId, String purpose, Long amount);

  WalletTransaction getTransaction(Wallet wallet) throws Exception;
}

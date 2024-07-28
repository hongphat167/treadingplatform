package com.treading.coin.service.impl;

import com.treading.coin.enums.WalletTransactionType;
import com.treading.coin.model.Wallet;
import com.treading.coin.model.WalletTransaction;
import com.treading.coin.repository.WalletTransactionRepository;
import com.treading.coin.service.WalletTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {

  @Autowired
  private WalletTransactionRepository walletTransactionRepository;

  @Override
  public WalletTransaction createTransaction(Wallet wallet,
      WalletTransactionType walletTransactionType, String transferId, String purpose, Long amount) {
    WalletTransaction walletTransaction = new WalletTransaction();
    walletTransaction.setWallet(wallet);
    walletTransaction.setWalletTransactionType(walletTransactionType);
    walletTransaction.setTransferId(transferId);
    walletTransaction.setPurpose(purpose);
    walletTransaction.setAmount(amount);

    return walletTransactionRepository.save(walletTransaction);
  }

  @Override
  public WalletTransaction getTransaction(Wallet wallet) throws Exception {
    WalletTransaction walletTransaction = walletTransactionRepository.findByWalletId(wallet.getId());

    if(walletTransaction == null) {
      throw new Exception("transaction not found");
    }
    return walletTransaction;
  }
}

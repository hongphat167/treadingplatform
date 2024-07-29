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

  /**
   * Get Transaction
   *
   * @param wallet wallet
   * @return WalletTransaction
   * @throws Exception e
   */
  @Override
  public WalletTransaction getTransaction(Wallet wallet) throws Exception {
    WalletTransaction walletTransaction = walletTransactionRepository.findByWalletId(
        wallet.getId());

    if (walletTransaction == null) {
      throw new Exception("transaction not found");
    }
    return walletTransaction;
  }
}

package com.treading.coin.service;

import com.treading.coin.model.Order;
import com.treading.coin.model.User;
import com.treading.coin.model.Wallet;

public interface WalletService {

  /**
   * Get User Wallet
   *
   * @param user user
   * @return Wallet
   */
  Wallet getUserWallet(User user);

  /**
   * Ddd Balance
   *
   * @param wallet wallet
   * @param money money
   * @return Wallet
   */
  Wallet addBalance(Wallet wallet, Long money);

  /**
   * Find Wallet By Id
   *
   * @param id id
   * @return Wallet
   */
  Wallet findWalletById(Long id);

  /**
   * Wallet To Wallet Transfer
   *
   * @param sender sender
   * @param receiverWallet receiverWallet
   * @param amount amount
   * @return Wallet
   */
  Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;
  Wallet payOrderPayment(Order order, User user) throws Exception;

}

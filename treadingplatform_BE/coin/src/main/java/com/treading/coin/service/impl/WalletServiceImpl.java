package com.treading.coin.service.impl;

import com.treading.coin.enums.OrderType;
import com.treading.coin.model.Order;
import com.treading.coin.model.User;
import com.treading.coin.model.Wallet;
import com.treading.coin.repository.WalletRepository;
import com.treading.coin.service.WalletService;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

  @Autowired
  private WalletRepository walletRepository;

  /**
   * Get User Wallet
   *
   * @param user user
   * @return Wallet
   */
  @Override
  public Wallet getUserWallet(User user) {
    Wallet wallet = walletRepository.findByUserId(user.getId());
    if (wallet == null) {
      wallet = new Wallet();
      wallet.setUser(user);
    }
    return wallet;
  }

  /**
   * Add Balance
   *
   * @param wallet wallet
   * @param money  money
   * @return Wallet
   */
  @Override
  public Wallet addBalance(Wallet wallet, Long money) {
    BigDecimal balance = wallet.getBalance();
    BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));
    wallet.setBalance(newBalance);
    return walletRepository.save(wallet);
  }

  /**
   * Find Wallet ById
   *
   * @param id id
   * @return Wallet
   */
  @Override
  public Wallet findWalletById(Long id) {
    Optional<Wallet> wallet = Optional.of(walletRepository.findById(id).orElseThrow());
    return wallet.get();
  }

  /**
   * Wallet To Wallet Transfer
   *
   * @param sender         sender
   * @param receiverWallet receiverWallet
   * @param amount         amount
   * @return Wallet
   * @throws Exception e
   */
  @Override
  public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount)
      throws Exception {
    // Get user send balance
    Wallet senderWallet = getUserWallet(sender);
    if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
      throw new Exception("Insufficient balance....");
    }
    // Get sender balance from sender wallet
    BigDecimal senderBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
    senderWallet.setBalance(senderBalance);
    walletRepository.save(senderWallet);

    BigDecimal receiverBalance = receiverWallet.getBalance().add(senderBalance);
    receiverWallet.setBalance(receiverBalance);
    walletRepository.save(receiverWallet);
    return senderWallet;
  }

  /**
   * Pay Order Payment
   *
   * @param order order
   * @param user  user
   * @return wallet
   * @throws Exception e
   */
  @Override
  public Wallet payOrderPayment(Order order, User user) throws Exception {
    Wallet wallet = getUserWallet(user);
    BigDecimal newBalance;
    if (order.getOrderType().equals(OrderType.BUY)) {
      newBalance = wallet.getBalance().subtract(order.getPrice());
      if (newBalance.compareTo(order.getPrice()) < 0) {
        throw new Exception("Insufficient funds for this transaction");
      }
    } else {
      newBalance = wallet.getBalance().add(order.getPrice());
    }
    wallet.setBalance(newBalance);
    walletRepository.save(wallet);
    return wallet;
  }
}

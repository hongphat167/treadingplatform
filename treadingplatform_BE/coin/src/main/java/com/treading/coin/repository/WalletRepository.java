package com.treading.coin.repository;

import com.treading.coin.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

  /**
   * Find By User Id
   *
   * @param userId userId
   * @return Wallet
   */
  Wallet findByUserId(Long userId);

}

package com.treading.coin.repository;

import com.treading.coin.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Coin repository.
 */
public interface CoinRepository extends JpaRepository<Coin, String> {

}

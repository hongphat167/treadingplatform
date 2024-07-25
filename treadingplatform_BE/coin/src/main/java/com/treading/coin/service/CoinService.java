package com.treading.coin.service;

import com.treading.coin.model.Coin;
import java.util.List;

public interface CoinService {

  /**
   * Get Coin List
   *
   * @param page page
   * @return List<Coin>
   */
  List<Coin> getCoinList(int page) throws Exception;

  /**
   * Get Market Chart
   *
   * @param coinId coinId
   * @param days   days
   * @return String
   */
  String getMarketChart(String coinId, int days) throws Exception;

  /**
   * Get Coin Details
   *
   * @param coinId coinId
   * @return String
   */
  String getCoinDetails(String coinId) throws Exception;

  /**
   * Find By Id
   *
   * @param coinId coinId
   * @return Coin
   */
  Coin findById(String coinId) throws Exception;

  /**
   * Search Coin
   *
   * @param keyword keyword
   * @return String
   */
  String searchCoin(String keyword) throws Exception;

  /**
   * Get Top 50 Coins By Market Rank
   *
   * @return String
   */
  String getTop50CoinsByMarketRank() throws Exception;

  /**
   * Get Trending Coins
   *
   * @return String
   */
  String getTrendingCoins() throws Exception;
}

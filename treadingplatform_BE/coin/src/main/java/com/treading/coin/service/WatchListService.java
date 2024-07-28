package com.treading.coin.service;

import com.treading.coin.model.Coin;
import com.treading.coin.model.User;
import com.treading.coin.model.WatchList;

public interface WatchListService {

  /**
   * Find User Watch List
   *
   * @param userId userId
   * @return WatchList
   */
  WatchList findUserWatchList(Long userId) throws Exception;

  /**
   * Create Watch List
   *
   * @param user user
   * @return WatchList
   */
  WatchList createWatchList(User user);

  /**
   * Find By Id
   *
   * @param id id
   * @return WatchList
   */
  WatchList findById(Long id) throws Exception;

  /**
   * Add Item To Watch List
   *
   * @param coin coin
   * @param user user
   * @return Coin
   */
  Coin addItemToWatchList(Coin coin, User user) throws Exception;
}

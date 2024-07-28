package com.treading.coin.service.impl;

import com.treading.coin.model.Coin;
import com.treading.coin.model.User;
import com.treading.coin.model.WatchList;
import com.treading.coin.repository.WatchListRepository;
import com.treading.coin.service.WatchListService;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchListServiceImpl implements WatchListService {

  @Autowired
  private WatchListRepository watchListRepository;

  /**
   * Find User Watch List
   *
   * @param userId userId
   * @return WatchList
   */
  @Override
  public WatchList findUserWatchList(Long userId) throws Exception {
    WatchList watchList = watchListRepository.findByUserId(userId);

    if (watchList == null) {
      throw new Exception("Watch list not found");
    }
    return watchList;
  }

  /**
   * Create Watch List
   *
   * @param user user
   * @return WatchList
   */
  @Override
  public WatchList createWatchList(User user) {
    WatchList watchList = new WatchList();
    watchList.setCoins(new ArrayList<>());
    watchList.setUser(user);
    return watchListRepository.save(watchList);
  }

  /**
   * Find By Id
   *
   * @param id id
   * @return WatchList
   */
  @Override
  public WatchList findById(Long id) throws Exception {
    Optional<WatchList> watchListOptional = watchListRepository.findById(id);

    if (watchListOptional.isEmpty()) {
      throw new Exception("Watch list not found");
    }
    return watchListOptional.get();
  }

  /**
   * Add Item To Watch List
   *
   * @param coin coin
   * @param user user
   * @return Coin
   */
  @Override
  public Coin addItemToWatchList(Coin coin, User user) throws Exception {
    WatchList watchList = findUserWatchList(user.getId());

    if (watchList.getCoins().contains(coin)) {
      watchList.getCoins().remove(coin);
    } else {
      watchList.getCoins().add(coin);
    }
    watchListRepository.save(watchList);

    return coin;
  }
}

package com.treading.coin.repository;

import com.treading.coin.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchListRepository extends JpaRepository<WatchList, Long> {

  /**
   * Find By User Id
   *
   * @param userId userId
   * @return WatchList
   */
  WatchList findByUserId(Long userId);

}

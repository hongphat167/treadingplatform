package com.treading.coin.repository;

import com.treading.coin.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchListRepository extends JpaRepository<WatchList, Long> {

  WatchList findByUserId(Long userId);

}

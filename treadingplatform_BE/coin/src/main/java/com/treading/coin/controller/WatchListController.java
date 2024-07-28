package com.treading.coin.controller;

import com.treading.coin.model.Coin;
import com.treading.coin.model.User;
import com.treading.coin.model.WatchList;
import com.treading.coin.service.CoinService;
import com.treading.coin.service.UserService;
import com.treading.coin.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/watch-list")
public class WatchListController {

  @Autowired
  private WatchListService watchListService;

  @Autowired
  private UserService userService;
  @Autowired
  private CoinService coinService;

  /**
   * /api/watch-list/user
   */
  @GetMapping("/user")
  public ResponseEntity<WatchList> getUserWatchList(@RequestHeader("Authorization") String jwt)
      throws Exception {

    User user = userService.findUserProfileByJwt(jwt);

    WatchList watchList = watchListService.findUserWatchList(user.getId());

    return new ResponseEntity<>(watchList, HttpStatus.OK);
  }

  /**
   * /api/watch-list/{watchListId}
   */
  @GetMapping("/{watchListId}")
  public ResponseEntity<WatchList> getWatchListById(@PathVariable Long watchListId)
      throws Exception {
    WatchList watchList = watchListService.findById(watchListId);

    return new ResponseEntity<>(watchList, HttpStatus.OK);
  }

  /**
   * /api/watch-list/add/coin/{coinId}
   */
  @PatchMapping("/add/coin/{coinId}")
  public ResponseEntity<Coin> addItemToWatchList(@RequestHeader("Authorization") String jwt,
      @PathVariable String coinId)
      throws Exception {

    User user = userService.findUserProfileByJwt(jwt);
    Coin coin = coinService.findById(coinId);

    Coin addedCoin = watchListService.addItemToWatchList(coin, user);

    return new ResponseEntity<>(addedCoin, HttpStatus.OK);
  }
}

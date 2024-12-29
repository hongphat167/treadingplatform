package com.treading.coin.controller;

import com.treading.coin.model.Coin;
import com.treading.coin.model.User;
import com.treading.coin.model.WatchList;
import com.treading.coin.service.CoinService;
import com.treading.coin.service.UserService;
import com.treading.coin.service.WatchListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Watch list controller.
 */
@RestController
@RequestMapping("/api/watch-list")
public class WatchListController {


	private final WatchListService watchListService;

	private final UserService userService;

	private final CoinService coinService;

	/**
	 * Instantiates a new Watch list controller.
	 *
	 * @param watchListService the watch list service
	 * @param userService      the user service
	 * @param coinService      the coin service
	 */
	protected WatchListController(WatchListService watchListService,
	                              UserService userService,
	                              CoinService coinService) {
		this.watchListService = watchListService;
		this.userService = userService;
		this.coinService = coinService;
	}

	/**
	 * Gets user watch list.
	 *
	 * @param jwt the jwt
	 * @return the user watch list
	 * @throws Exception the exception
	 */
	@GetMapping("/user")
	public ResponseEntity<WatchList> getUserWatchList(@RequestHeader("Authorization") String jwt)
			throws Exception {

		User user = userService.findUserProfileByJwt(jwt);

		WatchList watchList = watchListService.findUserWatchList(user.getId());

		return new ResponseEntity<>(watchList, HttpStatus.OK);
	}

	/**
	 * Gets watch list by id.
	 *
	 * @param watchListId the watch list id
	 * @return the watch list by id
	 * @throws Exception the exception
	 */
	@GetMapping("/{watchListId}")
	public ResponseEntity<WatchList> getWatchListById(@PathVariable Long watchListId)
			throws Exception {
		WatchList watchList = watchListService.findById(watchListId);

		return new ResponseEntity<>(watchList, HttpStatus.OK);
	}

	/**
	 * Add item to watch list response entity.
	 *
	 * @param jwt    the jwt
	 * @param coinId the coin id
	 * @return the response entity
	 * @throws Exception the exception
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

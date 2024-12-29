package com.treading.coin.service;

import com.treading.coin.model.Coin;
import com.treading.coin.model.User;
import com.treading.coin.model.WatchList;

/**
 * The interface Watch list service.
 */
public interface WatchListService {

	/**
	 * Find user watch list watch list.
	 *
	 * @param userId the user id
	 * @return the watch list
	 * @throws Exception the exception
	 */
	WatchList findUserWatchList(Long userId) throws Exception;

	/**
	 * Create watch list watch list.
	 *
	 * @param user the user
	 */
	void createWatchList(User user);

	/**
	 * Find by id watch list.
	 *
	 * @param id the id
	 * @return the watch list
	 * @throws Exception the exception
	 */
	WatchList findById(Long id) throws Exception;

	/**
	 * Add item to watch list coin.
	 *
	 * @param coin the coin
	 * @param user the user
	 * @return the coin
	 * @throws Exception the exception
	 */
	Coin addItemToWatchList(Coin coin, User user) throws Exception;
}

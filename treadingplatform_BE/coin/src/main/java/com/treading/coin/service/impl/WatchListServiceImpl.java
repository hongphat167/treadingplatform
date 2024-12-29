package com.treading.coin.service.impl;

import com.treading.coin.model.Coin;
import com.treading.coin.model.User;
import com.treading.coin.model.WatchList;
import com.treading.coin.repository.WatchListRepository;
import com.treading.coin.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * The type Watch list service.
 */
@Service
public class WatchListServiceImpl implements WatchListService {

	@Autowired
	private WatchListRepository watchListRepository;

	@Override
	public WatchList findUserWatchList(Long userId) throws Exception {
		WatchList watchList = watchListRepository.findByUserId(userId);

		if (watchList == null) {
			throw new Exception("Watch list not found");
		}
		return watchList;
	}

	@Override
	public void createWatchList(User user) {
		WatchList watchList = new WatchList();
		watchList.setCoins(new ArrayList<>());
		watchList.setUser(user);
		watchListRepository.save(watchList);
	}

	@Override
	public WatchList findById(Long id) throws Exception {
		Optional<WatchList> watchListOptional = watchListRepository.findById(id);

		if (watchListOptional.isEmpty()) {
			throw new Exception("Watch list not found");
		}
		return watchListOptional.get();
	}

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

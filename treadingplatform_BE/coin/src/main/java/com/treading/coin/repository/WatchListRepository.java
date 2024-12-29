package com.treading.coin.repository;

import com.treading.coin.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Watch list repository.
 */
public interface WatchListRepository extends JpaRepository<WatchList, Long> {

	/**
	 * Find by user id watch list.
	 *
	 * @param userId the user id
	 * @return the watch list
	 */
	WatchList findByUserId(Long userId);

}

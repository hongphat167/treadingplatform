package com.treading.coin.repository;

import com.treading.coin.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Order repository.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

	/**
	 * Find by user id list.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	List<Order> findByUserId(Long userId);

}

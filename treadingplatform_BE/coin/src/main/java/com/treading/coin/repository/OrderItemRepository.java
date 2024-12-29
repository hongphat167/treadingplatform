package com.treading.coin.repository;

import com.treading.coin.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Order item repository.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}

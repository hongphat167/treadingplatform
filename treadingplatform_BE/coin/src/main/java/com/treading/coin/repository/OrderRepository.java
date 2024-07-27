package com.treading.coin.repository;

import com.treading.coin.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  /**
   * Find By User Id
   *
   * @param userId userId
   * @return List<Order>
   */
  List<Order> findByUserId(Long userId);

}

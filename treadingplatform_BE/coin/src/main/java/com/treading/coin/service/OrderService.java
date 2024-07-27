package com.treading.coin.service;

import com.treading.coin.enums.OrderType;
import com.treading.coin.model.Coin;
import com.treading.coin.model.Order;
import com.treading.coin.model.OrderItem;
import com.treading.coin.model.User;
import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

  /**
   * Create Order
   *
   * @param user      user
   * @param orderItem orderItem
   * @param orderType orderType
   * @return Order
   */
  Order createOrder(User user, OrderItem orderItem, OrderType orderType);

  /**
   * Get Order By Id
   *
   * @param orderId orderId
   * @return Order
   * @throws Exception e
   */
  Order getOrderById(Long orderId) throws Exception;

  /**
   * Get All Order Of User
   *
   * @param userId      userId
   * @param orderType   orderType
   * @param assetSymbol assetSymbol
   * @return List<Order>
   */

  List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol);

  /**
   * Process Order
   *
   * @param coin      coin
   * @param quantity  quantity
   * @param orderType orderType
   * @param user      user
   * @return Order
   * @throws Exception e
   */
  Order processOrder(Coin coin, BigDecimal quantity, OrderType orderType, User user)
      throws Exception;

}

package com.treading.coin.service;

import com.treading.coin.enums.OrderType;
import com.treading.coin.model.Coin;
import com.treading.coin.model.Order;
import com.treading.coin.model.OrderItem;
import com.treading.coin.model.User;
import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

  Order createOrder(User user, OrderItem orderItem, OrderType orderType);
  Order getOrderById(Long orderId) throws Exception;

  List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol);

  Order processOrder(Coin coin, BigDecimal quantity, OrderType orderType, User user) throws Exception;

}

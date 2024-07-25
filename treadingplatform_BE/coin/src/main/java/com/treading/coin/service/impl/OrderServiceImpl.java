package com.treading.coin.service.impl;

import com.treading.coin.enums.OrderStatus;
import com.treading.coin.enums.OrderType;
import com.treading.coin.model.Coin;
import com.treading.coin.model.Order;
import com.treading.coin.model.OrderItem;
import com.treading.coin.model.User;
import com.treading.coin.repository.OrderItemRepository;
import com.treading.coin.repository.OrderRepository;
import com.treading.coin.service.OrderService;
import com.treading.coin.service.WalletService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private WalletService walletService;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Override
  public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
    BigDecimal price = orderItem.getCoin().getCurrentPrice().multiply(
        BigDecimal.valueOf(orderItem.getQuantity()));

    Order order = new Order();
    order.setUser(user);
    order.setOrderItem(orderItem);
    order.setOrderType(orderType);
    order.setPrice(price);
    order.setTimestamp(LocalDateTime.now());
    order.setStatus(OrderStatus.PENDING);

    return orderRepository.save(order);
  }

  @Override
  public Order getOrderById(Long orderId) throws Exception {
    return orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
  }

  @Override
  public List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol) {
    return orderRepository.findByUserId(userId);
  }

  private OrderItem createOrderItem(Coin coin, Double quantity, BigDecimal buyPrice,
      BigDecimal sellPrice) {

    OrderItem orderItem = new OrderItem();
    orderItem.setCoin(coin);
    orderItem.setQuantity(quantity);
    orderItem.setBuyPrice(buyPrice);
    orderItem.setSellPrice(sellPrice);

    return orderItemRepository.save(orderItem);

  }

  @Transactional
  public Order buyAsset(Coin coin, Double quantity, User user) throws Exception {
    if (quantity <= 0) {
      throw new Exception("quantity should be > 0");
    }
    BigDecimal buyPrice = coin.getCurrentPrice();

    OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, BigDecimal.ZERO);

    Order order = createOrder(user, orderItem, OrderType.BUY);
    orderItem.setOrder(order);

    walletService.payOrderPayment(order, user);
    order.setStatus(OrderStatus.SUCCESS);
    order.setOrderType(OrderType.BUY);

    // create asset

    return orderRepository.save(order);
  }

  @Transactional
  public Order sellAsset(Coin coin, Double quantity, User user) throws Exception {
    if (quantity <= 0) {
      throw new Exception("quantity should be > 0");
    }
    BigDecimal sellPrice = coin.getCurrentPrice();

    BigDecimal buyPrice = assetToSell.getPrice();

    OrderItem orderItem = createOrderItem(coin, quantity, BigDecimal.ZERO, sellPrice);

    Order order = createOrder(user, orderItem, OrderType.SELL);
    orderItem.setOrder(order);

    if (assetToSell.getQuantity() >= quantity) {
      walletService.payOrderPayment(order, user);

      Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);
      if (updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
        assetService.deleleAsset(updatedAsset.getId());
      }
      return savedOrder;
    }

    order.setStatus(OrderStatus.SUCCESS);
    order.setOrderType(OrderType.SELL);

    // create asset

    return orderRepository.save(order);
  }

  @Override
  public Order processOrder(Coin coin, Double quantity, OrderType orderType, User user) {
    return null;
  }
}

package com.treading.coin.service.impl;

import com.treading.coin.enums.OrderStatus;
import com.treading.coin.enums.OrderType;
import com.treading.coin.model.Asset;
import com.treading.coin.model.Coin;
import com.treading.coin.model.Order;
import com.treading.coin.model.OrderItem;
import com.treading.coin.model.User;
import com.treading.coin.repository.OrderItemRepository;
import com.treading.coin.repository.OrderRepository;
import com.treading.coin.service.AssetService;
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
  @Autowired
  private AssetService assetService;

  /**
   * Create Order
   *
   * @param user      user
   * @param orderItem orderItem
   * @param orderType orderType
   * @return Order
   */
  @Override
  public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
    BigDecimal price = orderItem.getCoin().getCurrentPrice()
        .multiply(orderItem.getQuantity());

    Order order = new Order();
    order.setUser(user);
    order.setOrderItem(orderItem);
    order.setOrderType(orderType);
    order.setPrice(price);
    order.setTimestamp(LocalDateTime.now());
    order.setStatus(OrderStatus.PENDING);

    return orderRepository.save(order);
  }

  /**
   * Get Order By Id
   *
   * @param orderId orderId
   * @return Order
   * @throws Exception e
   */
  @Override
  public Order getOrderById(Long orderId) throws Exception {
    return orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
  }

  /**
   * Get All Order Of User
   *
   * @param userId      userId
   * @param orderType   orderType
   * @param assetSymbol assetSymbol
   * @return List<Order>
   */
  @Override
  public List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol) {
    return orderRepository.findByUserId(userId);
  }

  /**
   * Create Order Item
   *
   * @param coin      coin
   * @param quantity  quantity
   * @param buyPrice  buyPrice
   * @param sellPrice sellPrice
   * @return OrderItem
   */
  private OrderItem createOrderItem(Coin coin, BigDecimal quantity, BigDecimal buyPrice,
      BigDecimal sellPrice) {

    OrderItem orderItem = new OrderItem();
    orderItem.setCoin(coin);
    orderItem.setQuantity(quantity);
    orderItem.setBuyPrice(buyPrice);
    orderItem.setSellPrice(sellPrice);

    return orderItemRepository.save(orderItem);

  }

  /**
   * Buy Asset
   *
   * @param coin     coin
   * @param quantity quantity
   * @param user     user
   * @return Order
   * @throws Exception e
   */
  @Transactional
  public Order buyAsset(Coin coin, BigDecimal quantity, User user) throws Exception {
    if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
      throw new Exception("quantity should be > 0");
    }
    BigDecimal buyPrice = coin.getCurrentPrice();

    OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, BigDecimal.ZERO);

    Order order = createOrder(user, orderItem, OrderType.BUY);
    orderItem.setOrder(order);

    walletService.payOrderPayment(order, user);
    order.setStatus(OrderStatus.SUCCESS);
    order.setOrderType(OrderType.BUY);
    Order savedOrder = orderRepository.save(order);

    Asset oldAsset = assetService.findAssetByUserIdAndCoinId(order.getUser().getId(),
        order.getOrderItem().getCoin().getId());
    if (oldAsset == null) {
      assetService.createAsset(user, orderItem.getCoin(), orderItem.getQuantity());
    } else {
      assetService.updateAsset(oldAsset.getId(), quantity);
    }
    return savedOrder;
  }

  /**
   * Sell Asset
   *
   * @param coin     coin
   * @param quantity quantity
   * @param user     user
   * @return Order
   * @throws Exception e
   */
  @Transactional
  public Order sellAsset(Coin coin, BigDecimal quantity, User user) throws Exception {
    if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
      throw new Exception("quantity should be > 0");
    }
    BigDecimal sellPrice = coin.getCurrentPrice();

    Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());
    BigDecimal buyPrice = assetToSell.getBuyPrice();
    if (assetToSell != null) {
      OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);

      Order order = createOrder(user, orderItem, OrderType.SELL);
      orderItem.setOrder(order);

      if (assetToSell.getQuantity().compareTo(quantity) >= 0) {

        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.SELL);
        Order savedOrder = orderRepository.save(order);
        walletService.payOrderPayment(order, user);

        Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), quantity.negate());

        if ((coin.getCurrentPrice().multiply(updatedAsset.getQuantity()).compareTo(BigDecimal.ONE))
            >= 0) {
          assetService.deleteAsset(updatedAsset.getId());
        }
        return savedOrder;
      }
      throw new Exception("Insufficient quantity to sell");
    }
    throw new Exception("asset not found");
  }

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
  @Override
  @Transactional
  public Order processOrder(Coin coin, BigDecimal quantity, OrderType orderType, User user)
      throws Exception {

    if (orderType.equals(OrderType.BUY)) {
      return buyAsset(coin, quantity, user);
    } else if (orderType.equals(OrderType.SELL)) {
      return sellAsset(coin, quantity, user);
    }
    throw new Exception("invalid order type");
  }
}

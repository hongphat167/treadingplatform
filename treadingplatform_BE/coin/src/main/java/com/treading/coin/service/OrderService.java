package com.treading.coin.service;

import com.treading.coin.enums.OrderType;
import com.treading.coin.model.Coin;
import com.treading.coin.model.Order;
import com.treading.coin.model.OrderItem;
import com.treading.coin.model.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * The interface Order service.
 */
public interface OrderService {

	/**
	 * Create order order.
	 *
	 * @param user      the user
	 * @param orderItem the order item
	 * @param orderType the order type
	 * @return the order
	 */
	Order createOrder(User user, OrderItem orderItem, OrderType orderType);

	/**
	 * Gets order by id.
	 *
	 * @param orderId the order id
	 * @return the order by id
	 * @throws Exception the exception
	 */
	Order getOrderById(Long orderId) throws Exception;

	/**
	 * Gets all order of user.
	 *
	 * @param userId      the user id
	 * @param orderType   the order type
	 * @param assetSymbol the asset symbol
	 * @return the all order of user
	 */
	List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol);

	/**
	 * Process order order.
	 *
	 * @param coin      the coin
	 * @param quantity  the quantity
	 * @param orderType the order type
	 * @param user      the user
	 * @return the order
	 * @throws Exception the exception
	 */
	Order processOrder(Coin coin, BigDecimal quantity, OrderType orderType, User user)
			throws Exception;

}

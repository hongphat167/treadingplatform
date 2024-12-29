package com.treading.coin.controller;

import com.treading.coin.controller.request.OrderRequest;
import com.treading.coin.enums.OrderType;
import com.treading.coin.model.Coin;
import com.treading.coin.model.Order;
import com.treading.coin.model.User;
import com.treading.coin.service.CoinService;
import com.treading.coin.service.OrderService;
import com.treading.coin.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Order controller.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {


	private final OrderService orderService;

	private final UserService userService;

	private final CoinService coinService;

	/**
	 * Instantiates a new Order controller.
	 *
	 * @param orderService the order service
	 * @param userService  the user service
	 * @param coinService  the coin service
	 */
	protected OrderController(OrderService orderService, UserService userService, CoinService coinService) {
		this.orderService = orderService;
		this.userService = userService;
		this.coinService = coinService;
	}

	//  private WalletTransactionService walletTransactionService;

	/**
	 * Pay order payment response entity.
	 *
	 * @param jwt     the jwt
	 * @param request the request
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/pay")
	public ResponseEntity<Order> payOrderPayment(@RequestHeader("Authorization") String jwt,
	                                             @RequestBody OrderRequest request) throws Exception {

		User user = userService.findUserProfileByJwt(jwt);
		Coin coin = coinService.findById(request.getCoinId());

		Order order = orderService.processOrder(coin, request.getQuantity(), request.getOrderType(),
				user);

		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	/**
	 * Gets order by id.
	 *
	 * @param jwt     the jwt
	 * @param orderId the order id
	 * @return the order by id
	 * @throws Exception the exception
	 */
	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(
			@RequestHeader("Authorization") String jwt, @PathVariable Long orderId) throws Exception {

		User user = userService.findUserProfileByJwt(jwt);
		Order order = orderService.getOrderById(orderId);

		if (order.getUser().getId().equals(user.getId())) {
			return new ResponseEntity<>(order, HttpStatus.OK);
		} else {
			throw new Exception("Invalid user");
		}
	}

	/**
	 * Gets all order for user.
	 *
	 * @param jwt         the jwt
	 * @param orderType   the order type
	 * @param assetSymbol the asset symbol
	 * @return the all order for user
	 * @throws Exception the exception
	 */
	@GetMapping("/get-all-order")
	public ResponseEntity<List<Order>> getAllOrderForUser(
			@RequestHeader("Authorization") String jwt,
			@RequestParam(required = false) OrderType orderType,
			@RequestParam(required = false) String assetSymbol) throws Exception {

		Long userId = userService.findUserProfileByJwt(jwt).getId();

		List<Order> userOrders = orderService.getAllOrderOfUser(userId, orderType, assetSymbol);
		return new ResponseEntity<>(userOrders, HttpStatus.OK);
	}

}

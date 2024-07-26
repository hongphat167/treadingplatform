package com.treading.coin.controller;

import com.treading.coin.controller.request.OrderRequest;
import com.treading.coin.enums.OrderType;
import com.treading.coin.model.Coin;
import com.treading.coin.model.Order;
import com.treading.coin.model.User;
import com.treading.coin.service.CoinService;
import com.treading.coin.service.OrderService;
import com.treading.coin.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;
  @Autowired
  private UserService userService;
  @Autowired
  private CoinService coinService;

  //  private WalletTransactionService walletTransactionService;
  @PostMapping("pay")
  public ResponseEntity<Order> payOrderPayment(@RequestHeader("Authorization") String jwt,
      @RequestBody OrderRequest request) throws Exception {

    User user = userService.findUserProfileByJwt(jwt);
    Coin coin = coinService.findById(request.getCoinId());

    Order order = orderService.processOrder(coin, request.getQuantity(), request.getOrderType(),
        user);

    return new ResponseEntity<>(order, HttpStatus.OK);
  }

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

  public ResponseEntity<List<Order>> getAllOrderForUser(
      @RequestHeader("Authorization") String jwt,
      @RequestParam(required = false) OrderType orderType,
      @RequestParam(required = false) String assetSymbol) throws Exception {

    Long userId = userService.findUserProfileByJwt(jwt).getId();

    List<Order> userOrders = orderService.getAllOrderOfUser(userId, orderType, assetSymbol);
    return new ResponseEntity<>(userOrders, HttpStatus.OK);
  }

}

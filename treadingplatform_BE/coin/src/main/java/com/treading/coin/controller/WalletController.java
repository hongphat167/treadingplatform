package com.treading.coin.controller;

import com.treading.coin.model.Order;
import com.treading.coin.model.User;
import com.treading.coin.model.Wallet;
import com.treading.coin.model.WalletTransaction;
import com.treading.coin.service.OrderService;
import com.treading.coin.service.UserService;
import com.treading.coin.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

  @Autowired
  private WalletService walletService;
  @Autowired
  private UserService userService;
  @Autowired
  private OrderService orderService;

  @GetMapping("/get-user-wallet")
  public ResponseEntity<Wallet> getUserWaller(@RequestHeader("Authorization") String jwt)
      throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    Wallet wallet = walletService.getUserWallet(user);
    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
  }

  @PutMapping("/{walletId}/transfer")
  public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt,
      @PathVariable Long walletId,
      @RequestBody WalletTransaction request) throws Exception {
    User senderUser = userService.findUserProfileByJwt(jwt);
    Wallet receiverWallet = walletService.findWalletById(walletId);
    Wallet wallet = walletService.walletToWalletTransfer(senderUser, receiverWallet,
        request.getAmount());

    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
  }

  @PutMapping("/{orderId}/pay")
  public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt,
      @PathVariable Long orderId,
      @RequestBody WalletTransaction request) throws Exception {

    User user = userService.findUserProfileByJwt(jwt);
    Order order = orderService.getOrderById(orderId);
    Wallet wallet = walletService.payOrderPayment(order, user);

    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
  }
}

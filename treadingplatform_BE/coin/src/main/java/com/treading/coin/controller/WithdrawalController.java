package com.treading.coin.controller;

import com.treading.coin.model.User;
import com.treading.coin.model.Wallet;
import com.treading.coin.model.Withdrawal;
import com.treading.coin.service.UserService;
import com.treading.coin.service.WalletService;
import com.treading.coin.service.WithdrawalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {

  @Autowired
  private WithdrawalService withdrawalService;
  @Autowired
  private WalletService walletService;
  @Autowired
  private UserService userService;

  /**
   * /api/withdrawal/{amount}
   */
  @PostMapping("/{amount}")
  public ResponseEntity<Withdrawal> withdrawalRequest(@RequestHeader("Authorization") String jwt,
      @PathVariable Long amount) throws Exception {

    User user = userService.findUserProfileByJwt(jwt);

    Wallet userWallet = walletService.getUserWallet(user);

    Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);

    walletService.addBalance(userWallet, -withdrawal.getAmount());

    return new ResponseEntity<>(withdrawal, HttpStatus.OK);
  }

  /**
   * /api/withdrawal/{id}/process/{accept}
   */
  @PatchMapping("/{id}/process/{accept}")
  public ResponseEntity<Withdrawal> processWithdrawal(@PathVariable Long id,
      @PathVariable boolean accept,
      @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserProfileByJwt(jwt);

    Withdrawal withdrawal = withdrawalService.processWithdrawal(id, accept);

    Wallet userWallet = walletService.getUserWallet(user);

    if (!accept) {
      walletService.addBalance(userWallet, withdrawal.getAmount());
    }
    return new ResponseEntity<>(withdrawal, HttpStatus.OK);
  }

  /**
   * /api/withdrawal/get-withdrawal-history
   */
  @GetMapping("/get-withdrawal-history")
  public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
      @RequestHeader("Authorization") String jwt)
      throws Exception {

    User user = userService.findUserProfileByJwt(jwt);

    List<Withdrawal> withdrawalList = withdrawalService.getUsersWithdrawalHistory(user);

    return new ResponseEntity<>(withdrawalList, HttpStatus.OK);
  }

  /**
   * /api/withdrawal/get-list-withdrawal
   */
  @GetMapping("/get-list-withdrawal")
  public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(
      @RequestHeader("Authorization") String jwt)
      throws Exception {
    User user = userService.findUserProfileByJwt(jwt);

    List<Withdrawal> withdrawalList = withdrawalService.getAllWithdrawalRequest();

    return new ResponseEntity<>(withdrawalList, HttpStatus.OK);
  }
}

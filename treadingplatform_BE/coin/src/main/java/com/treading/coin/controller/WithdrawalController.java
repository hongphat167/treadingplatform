package com.treading.coin.controller;

import com.treading.coin.model.User;
import com.treading.coin.model.Wallet;
import com.treading.coin.model.Withdrawal;
import com.treading.coin.service.UserService;
import com.treading.coin.service.WalletService;
import com.treading.coin.service.WithdrawalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Withdrawal controller.
 */
@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {


	private final WithdrawalService withdrawalService;

	private final WalletService walletService;

	private final UserService userService;

	/**
	 * Instantiates a new Withdrawal controller.
	 *
	 * @param withdrawalService the withdrawal service
	 * @param walletService     the wallet service
	 * @param userService       the user service
	 */
	protected WithdrawalController(WithdrawalService withdrawalService,
	                               WalletService walletService,
	                               UserService userService) {
		this.withdrawalService = withdrawalService;
		this.walletService = walletService;
		this.userService = userService;
	}

	/**
	 * Withdrawal request response entity.
	 *
	 * @param jwt    the jwt
	 * @param amount the amount
	 * @return the response entity
	 * @throws Exception the exception
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
	 * Process withdrawal response entity.
	 *
	 * @param id     the id
	 * @param accept the accept
	 * @param jwt    the jwt
	 * @return the response entity
	 * @throws Exception the exception
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
	 * Gets withdrawal history.
	 *
	 * @param jwt the jwt
	 * @return the withdrawal history
	 * @throws Exception the exception
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
	 * Gets all withdrawal request.
	 *
	 * @param jwt the jwt
	 * @return the all withdrawal request
	 * @throws Exception the exception
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

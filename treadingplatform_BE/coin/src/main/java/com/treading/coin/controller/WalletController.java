package com.treading.coin.controller;

import com.treading.coin.enums.PaymentOrderStatus;
import com.treading.coin.enums.WalletTransactionType;
import com.treading.coin.model.*;
import com.treading.coin.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Wallet controller.
 */
@RestController
@RequestMapping("/api/wallet")
public class WalletController {


	private final WalletService walletService;

	private final UserService userService;

	private final OrderService orderService;

	private final PaymentService paymentService;

	private final WalletTransactionService walletTransactionService;

	/**
	 * Instantiates a new Wallet controller.
	 *
	 * @param walletService            the wallet service
	 * @param userService              the user service
	 * @param orderService             the order service
	 * @param paymentService           the payment service
	 * @param walletTransactionService the wallet transaction service
	 */
	protected WalletController(WalletService walletService,
	                           UserService userService,
	                           OrderService orderService,
	                           PaymentService paymentService,
	                           WalletTransactionService walletTransactionService) {
		this.walletService = walletService;
		this.userService = userService;
		this.orderService = orderService;
		this.paymentService = paymentService;
		this.walletTransactionService = walletTransactionService;
	}

	/**
	 * Gets user waller.
	 *
	 * @param jwt the jwt
	 * @return the user waller
	 * @throws Exception the exception
	 */
	@GetMapping("/get-user-wallet")
	public ResponseEntity<Wallet> getUserWaller(@RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userService.findUserProfileByJwt(jwt);
		Wallet wallet = walletService.getUserWallet(user);
		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}

	/**
	 * Wallet to wallet transfer response entity.
	 *
	 * @param jwt      the jwt
	 * @param walletId the wallet id
	 * @param request  the request
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PutMapping("/{walletId}/transfer")
	public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt,
	                                                     @PathVariable Long walletId,
	                                                     @RequestBody WalletTransaction request) throws Exception {
		User senderUser = userService.findUserProfileByJwt(jwt);
		Wallet receiverWallet = walletService.findWalletById(walletId);
		Wallet wallet = walletService.walletToWalletTransfer(senderUser, receiverWallet,
				request.getAmount());

		walletTransactionService.createTransaction(wallet, WalletTransactionType.WALLET_TRANSFER,
				String.valueOf(receiverWallet.getId()),
				request.getPurpose(), request.getAmount());

		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}

	/**
	 * Pay order payment response entity.
	 *
	 * @param jwt     the jwt
	 * @param orderId the order id
	 * @param request the request
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PutMapping("/{orderId}/pay")
	public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt,
	                                              @PathVariable Long orderId,
	                                              @RequestBody WalletTransaction request) throws Exception {

		User user = userService.findUserProfileByJwt(jwt);
		Order order = orderService.getOrderById(orderId);
		Wallet wallet = walletService.payOrderPayment(order, user);

		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}

	/**
	 * Add balance to wallet response entity.
	 *
	 * @param jwt     the jwt
	 * @param orderId the order id
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@GetMapping("/deposit")
	public ResponseEntity<Wallet> addBalanceToWallet(@RequestHeader("Authorization") String jwt,
	                                                 @RequestParam(name = "order_id") Long orderId) throws Exception {

		User user = userService.findUserProfileByJwt(jwt);
		Wallet wallet = walletService.getUserWallet(user);

		PaymentOrder order = paymentService.getPaymentOrderById(orderId);

		if (order.getPaymentOrderStatus().equals(PaymentOrderStatus.SUCCESS) && user.getId()
				.equals(order.getUser().getId())) {
			wallet = walletService.addBalance(wallet, order.getAmount());
		} else {
			throw new Exception("Wrong payment order");
		}
		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}

	/**
	 * Gets wallet transaction.
	 *
	 * @param jwt the jwt
	 * @return the wallet transaction
	 * @throws Exception the exception
	 */
	@GetMapping("/transactions")
	public ResponseEntity<WalletTransaction> getWalletTransaction(
			@RequestHeader("Authorization") String jwt)
			throws Exception {

		User user = userService.findUserProfileByJwt(jwt);
		Wallet wallet = walletService.getUserWallet(user);

		WalletTransaction walletTransaction = walletTransactionService.getTransaction(wallet);
		return new ResponseEntity<>(walletTransaction, HttpStatus.OK);
	}
}

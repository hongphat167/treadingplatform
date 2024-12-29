package com.treading.coin.service.impl;

import com.treading.coin.enums.WalletTransactionType;
import com.treading.coin.model.Wallet;
import com.treading.coin.model.WalletTransaction;
import com.treading.coin.repository.WalletTransactionRepository;
import com.treading.coin.service.WalletTransactionService;
import org.springframework.stereotype.Service;

/**
 * The type Wallet transaction service.
 */
@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {

	private final WalletTransactionRepository walletTransactionRepository;

	/**
	 * Instantiates a new Wallet transaction service.
	 *
	 * @param walletTransactionRepository the wallet transaction repository
	 */
	protected WalletTransactionServiceImpl(WalletTransactionRepository walletTransactionRepository) {
		this.walletTransactionRepository = walletTransactionRepository;
	}

	@Override
	public void createTransaction(Wallet wallet,
	                              WalletTransactionType walletTransactionType,
	                              String transferId,
	                              String purpose,
	                              Long amount) {
		WalletTransaction walletTransaction = new WalletTransaction();
		walletTransaction.setWallet(wallet);
		walletTransaction.setWalletTransactionType(walletTransactionType);
		walletTransaction.setTransferId(transferId);
		walletTransaction.setPurpose(purpose);
		walletTransaction.setAmount(amount);

		walletTransactionRepository.save(walletTransaction);
	}

	@Override
	public WalletTransaction getTransaction(Wallet wallet) throws Exception {
		WalletTransaction walletTransaction = walletTransactionRepository.findByWalletId(
				wallet.getId());

		if (walletTransaction == null) {
			throw new Exception("transaction not found");
		}
		return walletTransaction;
	}
}

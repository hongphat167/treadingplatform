package com.treading.coin.service.impl;


import com.treading.coin.enums.WithdrawalStatus;
import com.treading.coin.model.User;
import com.treading.coin.model.Withdrawal;
import com.treading.coin.repository.WithdrawalRepository;
import com.treading.coin.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The type Withdrawal service.
 */
@Service
public class WithdrawalServiceImpl implements WithdrawalService {


	private final WithdrawalRepository withdrawalRepository;

	/**
	 * Instantiates a new Withdrawal service.
	 *
	 * @param withdrawalRepository the withdrawal repository
	 */
	public WithdrawalServiceImpl(WithdrawalRepository withdrawalRepository) {
		this.withdrawalRepository = withdrawalRepository;
	}

	@Override
	public Withdrawal requestWithdrawal(Long amount, User user) {
		Withdrawal withdrawal = new Withdrawal();
		withdrawal.setAmount(amount);
		withdrawal.setUser(user);
		withdrawal.setWithdrawalStatus(WithdrawalStatus.PENDING);
		return withdrawalRepository.save(withdrawal);
	}

	@Override
	public Withdrawal processWithdrawal(Long withdrawalId, boolean accept) throws Exception {
		Optional<Withdrawal> withdrawal = withdrawalRepository.findById(withdrawalId);
		if (withdrawal.isEmpty()) {
			throw new Exception("withdrawal not found");
		}
		Withdrawal withdrawal1 = withdrawal.get();
		withdrawal1.setDateTime(LocalDateTime.now());

		if (accept) {
			withdrawal1.setWithdrawalStatus(WithdrawalStatus.SUCCESS);
		} else {
			withdrawal1.setWithdrawalStatus(WithdrawalStatus.PENDING);
		}
		return withdrawalRepository.save(withdrawal1);
	}

	@Override
	public List<Withdrawal> getUsersWithdrawalHistory(User user) {
		return withdrawalRepository.findByUserId(user.getId());
	}

	@Override
	public List<Withdrawal> getAllWithdrawalRequest() {
		return withdrawalRepository.findAll();
	}
}

package com.treading.coin.service;

import com.treading.coin.model.User;
import com.treading.coin.model.Withdrawal;
import java.util.List;

public interface WithdrawalService {
  Withdrawal requestWithdrawal(Long amount, User user);

  Withdrawal processWithdrawal(Long withdrawalId, boolean accept) throws Exception;

  List<Withdrawal> getUsersWithdrawalHistory(User user);

  List<Withdrawal> getAllWithdrawalRequest();
}

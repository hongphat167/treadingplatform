package com.treading.coin.service.impl;


import com.treading.coin.enums.WithdrawalStatus;
import com.treading.coin.model.User;
import com.treading.coin.model.Withdrawal;
import com.treading.coin.repository.WithdrawalRepository;
import com.treading.coin.service.WithdrawalService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

  @Autowired
  private WithdrawalRepository withdrawalRepository;

  /**
   * Request Withdrawal
   *
   * @param amount amount
   * @param user   user
   * @return Withdrawal
   */
  @Override
  public Withdrawal requestWithdrawal(Long amount, User user) {
    Withdrawal withdrawal = new Withdrawal();
    withdrawal.setAmount(amount);
    withdrawal.setUser(user);
    withdrawal.setWithdrawalStatus(WithdrawalStatus.PENDING);
    return withdrawalRepository.save(withdrawal);
  }

  /**
   * Process Withdrawal
   *
   * @param withdrawalId withdrawalIdm
   * @param accept       accept
   * @return Withdrawal
   */
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

  /**
   * Get Users Withdrawal History
   *
   * @param user user
   * @return List<Withdrawal>
   */
  @Override
  public List<Withdrawal> getUsersWithdrawalHistory(User user) {
    return withdrawalRepository.findByUserId(user.getId());
  }

  /**
   * Get All Withdrawal Request
   *
   * @return List<Withdrawal>
   */
  @Override
  public List<Withdrawal> getAllWithdrawalRequest() {
    return withdrawalRepository.findAll();
  }
}

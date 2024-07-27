package com.treading.coin.repository;

import com.treading.coin.model.Withdrawal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

  List<Withdrawal> findByUserId(Long userId);

}

package com.treading.coin.repository;

import com.treading.coin.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Withdrawal repository.
 */
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

	/**
	 * Find by user id list.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	List<Withdrawal> findByUserId(Long userId);
}

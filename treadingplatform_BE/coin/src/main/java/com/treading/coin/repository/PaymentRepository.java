package com.treading.coin.repository;

import com.treading.coin.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Payment repository.
 */
public interface PaymentRepository extends JpaRepository<PaymentOrder, Long> {

}

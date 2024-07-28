package com.treading.coin.repository;

import com.treading.coin.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentOrder, Long> {

}

package com.treading.coin.model;

import com.treading.coin.enums.PaymentMethod;
import com.treading.coin.enums.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.Data;

/**
 * The type Payment order.
 */
@Entity
@Table(name = "payment_order")
@Data
public class PaymentOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long amount;
	private PaymentOrderStatus paymentOrderStatus;
	private PaymentMethod paymentMethod;
	@ManyToOne
	private User user;
}

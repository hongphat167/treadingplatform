package com.treading.coin.model;

import com.treading.coin.enums.WithdrawalStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * The type Withdrawal.
 */
@Entity
@Table(name = "withdrawal")
@Data
public class Withdrawal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private WithdrawalStatus withdrawalStatus;
	private Long amount;
	@ManyToOne
	private User user;
	private LocalDateTime dateTime = LocalDateTime.now();

}

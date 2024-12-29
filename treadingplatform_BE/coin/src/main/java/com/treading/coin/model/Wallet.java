package com.treading.coin.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The type Wallet.
 */
@Entity
@Table(name = "app_wallet")
@Data
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne
	private User user;
	private BigDecimal balance;
}

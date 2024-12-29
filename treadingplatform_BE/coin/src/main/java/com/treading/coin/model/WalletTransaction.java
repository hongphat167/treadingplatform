package com.treading.coin.model;

import com.treading.coin.enums.WalletTransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * The type Wallet transaction.
 */
@Entity
@Table(name = "wallet_trans")
@Data
public class WalletTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private Wallet wallet;
	private WalletTransactionType walletTransactionType;
	private LocalDate date = LocalDate.now();
	private String transferId;
	private String purpose;
	private Long amount;

}

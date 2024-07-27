package com.treading.coin.model;

import com.treading.coin.enums.WalletTransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Table(name = "wallet_trans")
@Data
public class WalletTransaction {

  /**
   * id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /**
   * wallet
   */
  @ManyToOne
  private Wallet wallet;
  /**
   * wallet_transaction_type
   */
  private WalletTransactionType walletTransactionType;
  /**
   * date
   */
  private LocalDate date;
  /**
   * transfer_id
   */
  private String transferId;
  /**
   * purpose
   */
  private String purpose;
  /**
   * amount
   */
  private Long amount;

}

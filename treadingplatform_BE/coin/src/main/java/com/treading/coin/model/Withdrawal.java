package com.treading.coin.model;

import com.treading.coin.enums.WithdrawalStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "withdrawal")
@Data
public class Withdrawal {

  /**
   * id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /**
   * withdrawal_status
   */
  private WithdrawalStatus withdrawalStatus;
  /**
   * amount
   */
  private Long amount;
  /**
   * user
   */
  @ManyToOne
  private User user;
  /**
   * date_time
   */
  private LocalDateTime dateTime = LocalDateTime.now();

}

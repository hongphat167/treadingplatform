package com.treading.coin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "payment_details")
@Data
public class PaymentDetails {

  /**
   * id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /**
   * account_number
   */
  private String accountNumber;
  /**
   * account_holder_name
   */
  private String accountHolderName;
  /**
   * ifsc
   */
  private String ifsc;
  /**
   * bank_name
   */
  private String bankName;
  /**
   * user
   */
  @OneToOne
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private User user;
}

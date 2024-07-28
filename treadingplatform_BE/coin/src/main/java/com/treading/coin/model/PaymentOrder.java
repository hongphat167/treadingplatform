package com.treading.coin.model;

import com.treading.coin.enums.PaymentMethod;
import com.treading.coin.enums.PaymentOrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "payment_order")
@Data
public class PaymentOrder {

  /**
   * id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /**
   * amount
   */
  private Long amount;
  /**
   * payment_order_status
   */
  private PaymentOrderStatus paymentOrderStatus;
  /**
   * payment_method
   */
  private PaymentMethod paymentMethod;
  /**
   * user
   */
  @ManyToOne
  private User user;
}

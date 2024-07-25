package com.treading.coin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem {

  /**
   * id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /**
   * quantity
   */
  private Double quantity;
  /**
   * coin
   */
  @ManyToOne
  private Coin coin;
  /**
   * by_price
   */
  private BigDecimal buyPrice;
  /**
   * sell_price
   */
  private BigDecimal sellPrice;
  /**
   * order
   */
  @JsonIgnore
  @OneToOne
  private Order order;
}

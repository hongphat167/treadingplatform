package com.treading.coin.model;

import com.treading.coin.enums.OrderStatus;
import com.treading.coin.enums.OrderType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "app_order")
@Data
public class Order {

  /**
   * id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /**
   * user
   */
  @ManyToOne
  private User user;
  /**
   * order_type
   */
  @Column(nullable = false)
  private OrderType orderType;
  /**
   * price
   */
  @Column(nullable = false)
  private BigDecimal price;
  /**
   * timestamp
   */
  private LocalDateTime timestamp = LocalDateTime.now();
  /**
   * status
   */
  @Column(nullable = false)
  private OrderStatus status;
  /**
   * order_item
   */
  @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
  private OrderItem orderItem;
}

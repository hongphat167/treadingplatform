package com.treading.coin.controller.request;

import com.treading.coin.enums.OrderType;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderRequest {

  /**
   * coin_id
   */
  private String coinId;
  /**
   * quantity
   */
  private BigDecimal quantity;
  /**
   * order_type
   */
  private OrderType orderType;
}

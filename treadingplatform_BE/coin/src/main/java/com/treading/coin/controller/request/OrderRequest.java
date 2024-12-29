package com.treading.coin.controller.request;

import com.treading.coin.enums.OrderType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The type Order request.
 */
@Data
public class OrderRequest {

	private String coinId;
	private BigDecimal quantity;
	private OrderType orderType;
}

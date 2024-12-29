package com.treading.coin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The type Order item.
 */
@Entity
@Table(name = "order_item")
@Data
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private BigDecimal quantity;
	@ManyToOne
	private Coin coin;
	private BigDecimal buyPrice;
	private BigDecimal sellPrice;
	@JsonIgnore
	@OneToOne
	private Order order;
}

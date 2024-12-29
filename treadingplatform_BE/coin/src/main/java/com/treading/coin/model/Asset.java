package com.treading.coin.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The type Asset.
 */
@Entity
@Table(name = "asset")
@Data
public class Asset {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private BigDecimal quantity;
	private BigDecimal buyPrice;
	@ManyToOne
	private Coin coin;
	@ManyToOne
	private User user;
}

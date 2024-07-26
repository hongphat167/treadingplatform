package com.treading.coin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;

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

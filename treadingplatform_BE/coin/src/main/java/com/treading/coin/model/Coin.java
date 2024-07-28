package com.treading.coin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;

@Entity
@Table(name = "coins")
@Data
public class Coin {

  /**
   * id
   */
  @Id
  @JsonProperty("id")
  private String id;
  /**
   * symbol
   */
  @JsonProperty("symbol")
  private String symbol;
  /**
   * name
   */
  @JsonProperty("name")
  private String name;
  /**
   * image
   */
  @JsonProperty("image")
  private String image;
  /**
   * current_price
   */
  @JsonProperty("current_price")
  private BigDecimal currentPrice;
  /**
   * market_cap
   */
  @JsonProperty("market_cap")
  private BigDecimal marketCap;
  /**
   * market_cap_rank
   */
  @JsonProperty("market_cap_rank")
  private Integer marketCapRank;
  /**
   * fully_diluted_valuation
   */
  @JsonProperty("fully_diluted_valuation")
  private BigDecimal fullyDilutedValuation;
  /**
   * total_volume
   */
  @JsonProperty("total_volume")
  private BigDecimal totalVolume;
  /**
   * high_24h
   */
  @JsonProperty("high_24h")
  private BigDecimal high24h;
  /**
   * low_24h
   */
  @JsonProperty("low_24h")
  private BigDecimal low24h;
  /**
   * price_change_24h
   */
  @JsonProperty("price_change_24h")
  private Double priceChange24h;
  /**
   * price_change_percentage_24h
   */
  @JsonProperty("price_change_percentage_24h")
  private Double priceChangePercentage24h;
  /**
   * market_cap_change_24h
   */
  @JsonProperty("market_cap_change_24h")
  private BigDecimal marketCapChange24h;
  /**
   * market_cap_change_percentage_24h
   */
  @JsonProperty("market_cap_change_percentage_24h")
  private BigDecimal marketCapChangePercentage24h;
  /**
   * circulating_supply
   */
  @JsonProperty("circulating_supply")
  private BigDecimal circulatingSupply;
  /**
   * total_supply
   */
  @JsonProperty("total_supply")
  private BigDecimal totalSupply;
  /**
   * max_supply
   */
  @JsonProperty("max_supply")
  private BigDecimal maxSupply;
  /**
   * ath
   */
  @JsonProperty("ath")
  private BigDecimal ath;
  /**
   * ath_change_percentage
   */
  @JsonProperty("ath_change_percentage")
  private BigDecimal athChangePercentage;
  /**
   * ath_date
   */
  @JsonProperty("ath_date")
  private String athDate;
  /**
   * atl
   */
  @JsonProperty("atl")
  private BigDecimal atl;
  /**
   * atl_change_percentage
   */
  @JsonProperty("atl_change_percentage")
  private BigDecimal atlChangePercentage;
  /**
   * atl_date
   */
  @JsonProperty("atl_date")
  private String atlDate;
  /**
   * roi
   */
  @JsonProperty("roi")
  @JsonIgnore
  private String roi;
  /**
   * last_updated
   */
  @JsonProperty("last_updated")
  private String lastUpdated;
}

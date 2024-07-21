package com.treading.coin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "two_factor")
@Data
public class TwoFactorOTP {

  /**
   * id
   */
  @Id
  private String id;
  /**
   * otp
   */
  private String otp;
  /**
   * user
   */
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OneToOne
  private User user;
  /**
   * jwt
   */
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String jwt;
}

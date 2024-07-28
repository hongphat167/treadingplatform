package com.treading.coin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.treading.coin.enums.Role;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "app_user")
@Data
public class User {

  /**
   * id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /**
   * full_name
   */
  private String fullName;
  /**
   * email
   */
  private String email;
  /**
   * password
   */
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  /**
   * two_facto_auth
   */
  @Embedded
  private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
  /**
   * role
   */
  private Role role = Role.CUSTOMER;
}

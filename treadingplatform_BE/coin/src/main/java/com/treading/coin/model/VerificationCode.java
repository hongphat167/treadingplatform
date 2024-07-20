package com.treading.coin.model;

import com.treading.coin.enums.VerificationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class VerificationCode {

  /**
   * id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /**
   * otp
   */
  private String otp;
  /**
   * user
   */
  @OneToOne
  private User user;
  /**
   * email
   */
  private String email;
  /**
   * mobile
   */
  private String mobile;
  /**
   * verification_type
   */
  private VerificationType verificationType;
}

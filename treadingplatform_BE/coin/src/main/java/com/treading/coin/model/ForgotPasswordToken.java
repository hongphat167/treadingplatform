package com.treading.coin.model;

import com.treading.coin.enums.VerificationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "forgot_password")
@Data
public class ForgotPasswordToken {

  /**
   * id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;
  /**
   * user
   */
  @OneToOne
  private User user;
  /**
   * otp
   */
  private String otp;
  /**
   * verification_type
   */
  private VerificationType verificationType;
  /**
   * send_to
   */
  private String sendTo;

}

package com.treading.coin.model;

import com.treading.coin.enums.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

/**
 * The type Forgot password token.
 */
@Entity
@Table(name = "forgot_password")
@Data
public class ForgotPasswordToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	@OneToOne
	private User user;
	private String otp;
	private VerificationType verificationType;
	private String sendTo;

}

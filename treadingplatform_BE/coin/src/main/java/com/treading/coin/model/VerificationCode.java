package com.treading.coin.model;

import com.treading.coin.enums.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

/**
 * The type Verification code.
 */
@Entity
@Table(name = "verification_code")
@Data
public class VerificationCode {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String otp;
	@OneToOne
	private User user;
	private String email;
	private String mobile;
	private VerificationType verificationType;
}

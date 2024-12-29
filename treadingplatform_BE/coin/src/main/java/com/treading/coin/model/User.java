package com.treading.coin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.treading.coin.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

/**
 * The type User.
 */
@Entity
@Table(name = "app_user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String fullName;
	private String email;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	@Embedded
	private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
	private Role role = Role.CUSTOMER;
}

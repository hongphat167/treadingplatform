package com.treading.coin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

/**
 * The type Payment details.
 */
@Entity
@Table(name = "payment_details")
@Data
public class PaymentDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String accountNumber;
	private String accountHolderName;
	private String ifsc;
	private String bankName;
	@OneToOne
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private User user;
}

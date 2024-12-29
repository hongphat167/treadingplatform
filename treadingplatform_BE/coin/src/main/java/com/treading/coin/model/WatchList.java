package com.treading.coin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Watch list.
 */
@Entity
@Table(name = "watch_list")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne
	private User user;
	@ManyToMany
	private List<Coin> coins;
}

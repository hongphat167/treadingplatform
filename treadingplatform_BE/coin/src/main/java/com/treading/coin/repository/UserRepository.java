package com.treading.coin.repository;

import com.treading.coin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Find by email user.
	 *
	 * @param email the email
	 * @return the user
	 */
	User findByEmail(String email);
}

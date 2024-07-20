package com.treading.coin.repository;

import com.treading.coin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Find By Email
   *
   * @param email email
   * @return User
   */
  User findByEmail(String email);
}

package com.treading.coin.service;

import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.User;
import org.springframework.stereotype.Service;


public interface UserService {

  /**
   * Find User Profile By Jwt
   *
   * @param jwt jwt
   * @return User
   * @throws Exception e
   */
  public User findUserProfileByJwt(String jwt) throws Exception;

  /**
   * Find User By Email
   *
   * @param email email
   * @return User
   * @throws Exception e
   */
  public User findUserByEmail(String email) throws Exception;

  /**
   * Find User By UserId
   *
   * @param userId userId
   * @return User
   * @throws Exception e
   */
  public User findUserByUserId(Long userId) throws Exception;

  /**
   * Enable Two-Factor Authentication
   *
   * @param verificationType verificationType
   * @param sendTo           sendTo
   * @param user             user
   * @return User
   */
  public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo,
      User user);

  /**
   * Update Password
   *
   * @param user        user
   * @param newPassword newPassword
   * @return User
   */
  User updatePassword(User user, String newPassword);
}

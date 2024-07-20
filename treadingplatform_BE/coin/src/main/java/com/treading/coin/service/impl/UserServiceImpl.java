package com.treading.coin.service.impl;

import com.treading.coin.config.JwtProvider;
import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.TwoFactorAuth;
import com.treading.coin.model.User;
import com.treading.coin.repository.UserRepository;
import com.treading.coin.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  /**
   * Find User Profile By Jwt
   *
   * @param jwt jwt
   * @return user
   * @throws Exception e
   */
  @Override
  public User findUserProfileByJwt(String jwt) throws Exception {
    String email = JwtProvider.getEmailFromToken(jwt);
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new Exception("User not found");
    }
    return user;
  }

  /**
   * Find User By Email
   *
   * @param email email
   * @return user
   * @throws Exception e
   */
  @Override
  public User findUserByEmail(String email) throws Exception {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new Exception("User not found");
    }
    return user;
  }

  /**
   * Find User By UserId
   *
   * @param userId userId
   * @return user
   */
  @Override
  public User findUserByUserId(Long userId) {
    Optional<User> user = Optional.of(userRepository.findById(userId).orElseThrow());
    return user.get();
  }

  /**
   * Enable Two-Factor Authentication
   *
   * @param verificationType verificationType
   * @param sendTo           sendTo
   * @param user             user
   * @return user
   */
  @Override
  public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo,
      User user) {

    TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
    twoFactorAuth.setEnabled(true);
    twoFactorAuth.setSendTo(verificationType);

    user.setTwoFactorAuth(twoFactorAuth);

    return userRepository.save(user);
  }

  /**
   * Update Password
   *
   * @param user        user
   * @param newPassword newPassword
   * @return user
   */
  @Override
  public User updatePassword(User user, String newPassword) {
    user.setPassword(newPassword);
    return userRepository.save(user);
  }
}

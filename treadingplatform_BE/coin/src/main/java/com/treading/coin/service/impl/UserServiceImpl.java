package com.treading.coin.service.impl;

import com.treading.coin.config.JwtProvider;
import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.TwoFactorAuth;
import com.treading.coin.model.User;
import com.treading.coin.repository.UserRepository;
import com.treading.coin.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	/**
	 * Instantiates a new User service.
	 *
	 * @param userRepository the user repository
	 */
	protected UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws Exception {
		String email = JwtProvider.getEmailFromToken(jwt);
		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new Exception("User not found");
		}
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new Exception("User not found");
		}
		return user;
	}

	@Override
	public User findUserByUserId(Long userId) {
		Optional<User> user = Optional.of(userRepository.findById(userId).orElseThrow());
		return user.get();
	}

	@Override
	public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo,
	                                          User user) {

		TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
		twoFactorAuth.setEnabled(true);
		twoFactorAuth.setSendTo(verificationType);

		user.setTwoFactorAuth(twoFactorAuth);

		return userRepository.save(user);
	}

	@Override
	public void updatePassword(User user, String newPassword) {
		user.setPassword(newPassword);
		userRepository.save(user);
	}
}

package com.treading.coin.service.impl;

import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.ForgotPasswordToken;
import com.treading.coin.model.User;
import com.treading.coin.repository.ForgotPasswordRepository;
import com.treading.coin.service.ForgotPasswordService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The type Forgot password service.
 */
@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {


	private final ForgotPasswordRepository forgotPasswordRepository;

	protected ForgotPasswordServiceImpl(ForgotPasswordRepository forgotPasswordRepository) {
		this.forgotPasswordRepository = forgotPasswordRepository;
	}

	@Override
	public ForgotPasswordToken createToken(User user, String id, String otp,
	                                       VerificationType verificationType, String sendTo) {

		ForgotPasswordToken token = new ForgotPasswordToken();
		token.setUser(user);
		token.setSendTo(sendTo);
		token.setVerificationType(verificationType);
		token.setOtp(otp);
		token.setId(id);
		return forgotPasswordRepository.save(token);
	}

	@Override
	public ForgotPasswordToken findById(String id) {

		Optional<ForgotPasswordToken> token = forgotPasswordRepository.findById(id);
		return token.orElse(null);
	}

	@Override
	public ForgotPasswordToken findByUser(Long userId) {
		return forgotPasswordRepository.findByUserId(userId);
	}

	@Override
	public void deleteToken(ForgotPasswordToken forgotPasswordToken) {
		forgotPasswordRepository.delete(forgotPasswordToken);
	}
}

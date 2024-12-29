package com.treading.coin.service.impl;

import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.User;
import com.treading.coin.model.VerificationCode;
import com.treading.coin.repository.VerificationCodeRepository;
import com.treading.coin.service.VerificationCodeService;
import com.treading.coin.utils.OtpUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The type Verification code service.
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {


	private final VerificationCodeRepository verificationCodeRepository;

	/**
	 * Instantiates a new Verification code service.
	 *
	 * @param verificationCodeRepository the verification code repository
	 */
	protected VerificationCodeServiceImpl(VerificationCodeRepository verificationCodeRepository) {
		this.verificationCodeRepository = verificationCodeRepository;
	}

	@Override
	public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {

		VerificationCode verificationCode1 = new VerificationCode();
		verificationCode1.setOtp(OtpUtils.generateOTP());
		verificationCode1.setVerificationType(verificationType);
		verificationCode1.setUser(user);

		return verificationCodeRepository.save(verificationCode1);
	}

	@Override
	public VerificationCode getVerificationCodeById(Long id) {

		Optional<VerificationCode> verificationCode = Optional.of(
				verificationCodeRepository.findById(id).orElseThrow());

		return verificationCode.get();
	}

	@Override
	public VerificationCode getVerificationCodeByUser(Long userId) {

		return verificationCodeRepository.findByUserId(userId);
	}

	@Override
	public void deleteVerificationCodeById(VerificationCode verificationCode) {

		verificationCodeRepository.delete(verificationCode);

	}
}

package com.treading.coin.service.impl;

import com.treading.coin.model.TwoFactorOTP;
import com.treading.coin.model.User;
import com.treading.coin.repository.TwoFactorOtpRepository;
import com.treading.coin.service.TwoFactorOtpService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * The type Two factor otp service.
 */
@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService {


	private final TwoFactorOtpRepository twoFactorOtpRepository;

	/**
	 * Instantiates a new Two factor otp service.
	 *
	 * @param twoFactorOtpRepository the two factor otp repository
	 */
	protected TwoFactorOtpServiceImpl(TwoFactorOtpRepository twoFactorOtpRepository) {
		this.twoFactorOtpRepository = twoFactorOtpRepository;
	}

	@Override
	public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
		UUID uuid = UUID.randomUUID();

		String id = uuid.toString();

		TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
		twoFactorOTP.setOtp(otp);
		twoFactorOTP.setJwt(jwt);
		twoFactorOTP.setId(id);
		twoFactorOTP.setUser(user);

		return twoFactorOtpRepository.save(twoFactorOTP);

	}

	@Override
	public TwoFactorOTP findByUser(Long userId) {
		return twoFactorOtpRepository.findByUserId(userId);
	}

	@Override
	public TwoFactorOTP findById(String id) {
		Optional<TwoFactorOTP> otp = twoFactorOtpRepository.findById(id);
		return otp.orElse(null);
	}

	@Override
	public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp) {
		return twoFactorOTP.getOtp().equals(otp);
	}

	@Override
	public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP) {
		twoFactorOtpRepository.delete(twoFactorOTP);
	}
}

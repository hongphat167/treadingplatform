package com.treading.coin.controller;

import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.User;
import com.treading.coin.model.VerificationCode;
import com.treading.coin.service.EmailService;
import com.treading.coin.service.UserService;
import com.treading.coin.service.VerificationCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {


	private final UserService userService;

	private final EmailService emailService;

	private final VerificationCodeService verificationCodeService;

	/**
	 * Instantiates a new User controller.
	 *
	 * @param userService             the user service
	 * @param emailService            the email service
	 * @param verificationCodeService the verification code service
	 */
	protected UserController(UserService userService,
	                         EmailService emailService,
	                         VerificationCodeService verificationCodeService) {
		this.userService = userService;
		this.emailService = emailService;
		this.verificationCodeService = verificationCodeService;
	}

	/**
	 * Gets user profile.
	 *
	 * @param jwt the jwt
	 * @return the user profile
	 * @throws Exception the exception
	 */
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt)
			throws Exception {

		User user = userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Send verification otp response entity.
	 *
	 * @param jwt              the jwt
	 * @param verificationType the verification type
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/verification/{verificationType}/send-otp")
	public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwt,
	                                                  @PathVariable
	                                                  VerificationType verificationType) throws Exception {

		User user = userService.findUserProfileByJwt(jwt);
		VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(
				user.getId());
		if (verificationCode == null) {
			verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
		}
		if (verificationType.equals(VerificationType.EMAIL)) {
			emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
		}

		return new ResponseEntity<>("verification OTP sent successfully", HttpStatus.OK);
	}

	/**
	 * Enable two factor authentication response entity.
	 *
	 * @param otp the otp
	 * @param jwt the jwt
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PatchMapping("/enable-two-factor/verify-otp/{otp}")
	public ResponseEntity<User> enableTwoFactorAuthentication(
			@PathVariable String otp,
			@RequestHeader("Authorization") String jwt)
			throws Exception {

		User user = userService.findUserProfileByJwt(jwt);

		VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(
				user.getId());

		String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)
				? verificationCode.getEmail() : verificationCode.getMobile();

		boolean isVerified = verificationCode.getOtp().equals(otp);

		if (isVerified) {
			User updateUser = userService.enableTwoFactorAuthentication(
					verificationCode.getVerificationType(), sendTo, user);

			verificationCodeService.deleteVerificationCodeById(verificationCode);
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
		}
		throw new Exception("Wrong OTP");
	}
}

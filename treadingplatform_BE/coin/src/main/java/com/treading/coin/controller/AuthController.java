package com.treading.coin.controller;

import com.treading.coin.config.JwtProvider;
import com.treading.coin.controller.request.ForgotPasswordTokenRequest;
import com.treading.coin.controller.request.ResetPasswordRequest;
import com.treading.coin.controller.response.ApiResponse;
import com.treading.coin.controller.response.AuthResponse;
import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.ForgotPasswordToken;
import com.treading.coin.model.TwoFactorOTP;
import com.treading.coin.model.User;
import com.treading.coin.repository.UserRepository;
import com.treading.coin.service.*;
import com.treading.coin.utils.OtpUtils;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


/**
 * The type Auth controller.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {


	private final UserRepository userRepository;

	private final CustomerUserDetailService customerUserDetailService;

	private final TwoFactorOtpService twoFactorOtpService;

	private final EmailService emailService;

	private final UserService userService;

	private final ForgotPasswordService forgotPasswordService;

	private final VerificationCodeService verificationCodeService;

	private final WatchListService watchListService;

	/**
	 * Instantiates a new Auth controller.
	 *
	 * @param userRepository            the user repository
	 * @param customerUserDetailService the customer user detail service
	 * @param twoFactorOtpService       the two factor otp service
	 * @param emailService              the email service
	 * @param userService               the user service
	 * @param forgotPasswordService     the forgot password service
	 * @param verificationCodeService   the verification code service
	 * @param watchListService          the watch list service
	 */
	protected AuthController(UserRepository userRepository,
	                         CustomerUserDetailService customerUserDetailService,
	                         TwoFactorOtpService twoFactorOtpService,
	                         EmailService emailService, UserService userService,
	                         ForgotPasswordService forgotPasswordService,
	                         VerificationCodeService verificationCodeService,
	                         WatchListService watchListService) {
		this.userRepository = userRepository;
		this.customerUserDetailService = customerUserDetailService;
		this.twoFactorOtpService = twoFactorOtpService;
		this.emailService = emailService;
		this.userService = userService;
		this.forgotPasswordService = forgotPasswordService;
		this.verificationCodeService = verificationCodeService;
		this.watchListService = watchListService;
	}

	/**
	 * Login response entity.
	 *
	 * @param user the user
	 * @return the response entity
	 * @throws MessagingException the messaging exception
	 */
	@PostMapping("/signing")
	public ResponseEntity<AuthResponse> login(@RequestBody User user) throws MessagingException {

		String userName = user.getEmail();
		String password = user.getPassword();

		Authentication auth = authenticate(userName, password);

		SecurityContextHolder.getContext().setAuthentication(auth);

		String jwt = JwtProvider.generateToken(auth);

		User authUser = userRepository.findByEmail(userName);

		if (user.getTwoFactorAuth().isEnabled()) {
			AuthResponse response = new AuthResponse();
			response.setMessage("Two factor auth is enabled");
			response.setTwoFactorAuthEnabled(true);
			String otp = OtpUtils.generateOTP();

			TwoFactorOTP oldtwoFactorOTP = twoFactorOtpService.findByUser(authUser.getId());
			if (oldtwoFactorOTP != null) {
				twoFactorOtpService.deleteTwoFactorOtp(oldtwoFactorOTP);
			}
			TwoFactorOTP newTwoFactorOtp = twoFactorOtpService.createTwoFactorOtp(authUser, otp, jwt);

			emailService.sendVerificationOtpEmail(userName, otp);

			response.setSession(newTwoFactorOtp.getId());
			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		}

		AuthResponse response = new AuthResponse();
		response.setJwt(jwt);
		response.setStatus(true);
		response.setMessage("login success");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Register response entity.
	 *
	 * @param user the user
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

		User isEmailExits = userRepository.findByEmail(user.getEmail());

		// Check email exits
		if (isEmailExits != null) {
			throw new Exception("email is already used with another account");
		}
		// Create new user
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		newUser.setFullName(user.getFullName());

		User saveUser = userRepository.save(newUser);

		watchListService.createWatchList(saveUser);

		Authentication auth = new UsernamePasswordAuthenticationToken(
				user.getEmail(),
				user.getPassword()
		);

		SecurityContextHolder.getContext().setAuthentication(auth);

		String jwt = JwtProvider.generateToken(auth);

		AuthResponse response = new AuthResponse();
		response.setJwt(jwt);
		response.setStatus(true);
		response.setMessage("register success");

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * Verify sign in otp response entity.
	 *
	 * @param otp the otp
	 * @param id  the id
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/two-factor/otp/{otp}")
	public ResponseEntity<AuthResponse> verifySignInOtp(@PathVariable String otp,
	                                                    @RequestParam String id)
			throws Exception {
		TwoFactorOTP twoFactorOTP = twoFactorOtpService.findById(id);
		if (twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP, otp)) {
			AuthResponse response = new AuthResponse();
			response.setMessage("Two factor authentication verified");
			response.setTwoFactorAuthEnabled(true);
			response.setJwt(twoFactorOTP.getJwt());

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		throw new Exception("Invalid OTP");
	}

	/**
	 * Send forgot password otp response entity.
	 *
	 * @param request the request
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/user/reset-password/send-otp")
	public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
			@RequestBody ForgotPasswordTokenRequest request) throws Exception {

		User user = userService.findUserByEmail(request.getSendTo());
		String otp = OtpUtils.generateOTP();
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();

		ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());

		if (token == null) {
			token = forgotPasswordService.createToken(user, id, otp, request.getVerificationType(),
					request.getSendTo());
		}

		if (request.getVerificationType().equals(VerificationType.EMAIL)) {
			emailService.sendVerificationOtpEmail(user.getEmail(), token.getOtp());
		}

		AuthResponse response = new AuthResponse();
		response.setSession(token.getId());
		response.setMessage("Password reset successfully");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Reset password response entity.
	 *
	 * @param id      the id
	 * @param request the request
	 * @param jwt     the jwt
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PatchMapping("/user/reset-password/verify-otp")
	public ResponseEntity<ApiResponse> resetPassword(
			@RequestParam String id,
			@RequestBody ResetPasswordRequest request,
			@RequestHeader("Authorization") String jwt)
			throws Exception {

		ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);

		boolean isVerified = forgotPasswordToken.getOtp().equals(request.getOtp());

		if (isVerified) {
			userService.updatePassword(forgotPasswordToken.getUser(), request.getPassword());

			ApiResponse response = new ApiResponse();
			response.setMessage("Reset password successfully");

			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		}
		throw new Exception("Wrong OTP");
	}

	private Authentication authenticate(String userName, String password) {
		UserDetails userDetails = customerUserDetailService.loadUserByUsername(userName);

		if (userDetails == null) {
			throw new BadCredentialsException("invalid username");
		}
		if (!password.equals(userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, password,
				userDetails.getAuthorities());
	}
}

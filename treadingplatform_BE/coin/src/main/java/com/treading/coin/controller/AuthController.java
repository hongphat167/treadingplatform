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
import com.treading.coin.service.CustomerUserDetailService;
import com.treading.coin.service.EmailService;
import com.treading.coin.service.ForgotPasswordService;
import com.treading.coin.service.TwoFactorOtpService;
import com.treading.coin.service.UserService;
import com.treading.coin.service.VerificationCodeService;
import com.treading.coin.utils.OtpUtils;
import jakarta.mail.MessagingException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CustomerUserDetailService customerUserDetailService;
  @Autowired
  private TwoFactorOtpService twoFactorOtpService;
  @Autowired
  private EmailService emailService;
  @Autowired
  private UserService userService;
  @Autowired
  private ForgotPasswordService forgotPasswordService;
  @Autowired
  private VerificationCodeService verificationCodeService;

  @PostMapping("/signin")
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
    response.setStatus(true);
    response.setMessage("login success");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

    User isEmailExits = userRepository.findByEmail(user.getEmail());

    // Check email exits
    if (isEmailExits != null) {
      throw new Exception("email is already used with anorther account");
    }
    // Create new user
    User newUser = new User();
    newUser.setEmail(user.getEmail());
    newUser.setPassword(user.getPassword());
    newUser.setFullName(user.getFullName());

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

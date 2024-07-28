package com.treading.coin.controller;

import com.treading.coin.enums.VerificationType;
import com.treading.coin.model.User;
import com.treading.coin.model.VerificationCode;
import com.treading.coin.service.EmailService;
import com.treading.coin.service.UserService;
import com.treading.coin.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService userService;
  @Autowired
  private EmailService emailService;
  @Autowired
  private VerificationCodeService verificationCodeService;
  private String jwt;

  /**
   * /api/user/profile
   */
  @GetMapping("/profile")
  public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt)
      throws Exception {

    User user = userService.findUserProfileByJwt(jwt);
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  /**
   * /api/user/verification/{verificationType}/send-otp
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
   * /api/user/enable-two-factor/verify-otp/{otp}
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

package com.treading.coin.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
  private final JavaMailSender javaMailSender;

  @Autowired
  public EmailService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  /**
   * Send Verification Otp Email
   *
   * @param email email
   * @param otp   otp
   * @throws MessagingException e
   */
  public void sendVerificationOtpEmail(String email, String otp) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

    String subject = "Xác thực OTP";
    String text = "Mã xác thực OTP của bạn là " + otp;

    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(text);
    mimeMessageHelper.setTo(email);

    try {
      javaMailSender.send(mimeMessage);
    } catch (MailException e) {
      throw new MessagingException(e.getMessage());
    }
  }
}

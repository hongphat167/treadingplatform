package com.treading.coin.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * The type Email service.
 */
@Service
public class EmailService {
	private final JavaMailSender javaMailSender;

	/**
	 * Instantiates a new Email service.
	 *
	 * @param javaMailSender the java mail sender
	 */
	protected EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	/**
	 * Send verification otp email.
	 *
	 * @param email the email
	 * @param otp   the otp
	 * @throws MessagingException the messaging exception
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

package com.treading.coin.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * The type Vn pay config.
 */
@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VnPayConfig {

	@Value("${vnpay.payUrl}")
	private String vnp_PayUrl;

	@Value("${vnpay.returnUrl}")
	private String vnp_ReturnUrl;

	@Value("${vnpay.tmnCode}")
	private String vnp_TmnCode;

	@Value("${vnpay.hashSecret}")
	private String vnp_HashSecret;

	@Value("${vnpay.apiUrl}")
	private String vnp_ApiUrl;

	/**
	 * Hash all fields string.
	 *
	 * @param fields the fields
	 * @return the string
	 */
	public String hashAllFields(Map<String, String> fields) {
		List<String> fieldNames = new ArrayList<>(fields.keySet());
		Collections.sort(fieldNames);
		StringBuilder sb = new StringBuilder();
		Iterator<String> itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = itr.next();
			String fieldValue = fields.get(fieldName);
			if (fieldValue != null && fieldValue.length() > 0) {
				sb.append(fieldName).append("=").append(fieldValue);
			}
			if (itr.hasNext()) {
				sb.append("&");
			}
		}
		return hmacSHA512(vnp_HashSecret, sb.toString());
	}

	/**
	 * Hmac sha 512 string.
	 *
	 * @param key  the key
	 * @param data the data
	 * @return the string
	 */
	public String hmacSHA512(final String key, final String data) {
		try {
			if (key == null || data == null) {
				throw new NullPointerException();
			}
			final Mac hmac512 = Mac.getInstance("HmacSHA512");
			byte[] hmacKeyBytes = key.getBytes();
			final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
			hmac512.init(secretKey);
			byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
			byte[] result = hmac512.doFinal(dataBytes);
			StringBuilder sb = new StringBuilder(2 * result.length);
			for (byte b : result) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * Gets random number.
	 *
	 * @param len the len
	 * @return the random number
	 */
	public String getRandomNumber(int len) {
		Random rnd = new Random();
		String chars = "0123456789";
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return sb.toString();
	}
}

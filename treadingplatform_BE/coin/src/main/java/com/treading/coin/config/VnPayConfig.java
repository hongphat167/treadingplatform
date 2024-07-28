package com.treading.coin.config;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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

//  public String md5(String message) {
//    String digest = null;
//    try {
//      MessageDigest md = MessageDigest.getInstance("MD5");
//      byte[] hash = md.digest(message.getBytes("UTF-8"));
//      StringBuilder sb = new StringBuilder(2 * hash.length);
//      for (byte b : hash) {
//        sb.append(String.format("%02x", b & 0xff));
//      }
//      digest = sb.toString();
//    } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
//      digest = "";
//    }
//    return digest;
//  }

//  public String sha256(String message) {
//    String digest = null;
//    try {
//      MessageDigest md = MessageDigest.getInstance("SHA-256");
//      byte[] hash = md.digest(message.getBytes("UTF-8"));
//      StringBuilder sb = new StringBuilder(2 * hash.length);
//      for (byte b : hash) {
//        sb.append(String.format("%02x", b & 0xff));
//      }
//      digest = sb.toString();
//    } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
//      digest = "";
//    }
//    return digest;
//  }

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

//  public String getIpAddress(HttpServletRequest request) {
//    String ipAdress;
//    try {
//      ipAdress = request.getHeader("X-FORWARDED-FOR");
//      if (ipAdress == null) {
//        ipAdress = request.getRemoteAddr();
//      }
//    } catch (Exception e) {
//      ipAdress = "Invalid IP:" + e.getMessage();
//    }
//    return ipAdress;
//  }

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

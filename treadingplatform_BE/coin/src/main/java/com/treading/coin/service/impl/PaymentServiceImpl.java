package com.treading.coin.service.impl;

import com.treading.coin.config.VnPayConfig;
import com.treading.coin.controller.response.PaymentResponse;
import com.treading.coin.enums.PaymentMethod;
import com.treading.coin.enums.PaymentOrderStatus;
import com.treading.coin.model.PaymentOrder;
import com.treading.coin.model.User;
import com.treading.coin.repository.PaymentRepository;
import com.treading.coin.service.PaymentService;
import com.treading.coin.utils.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private VnPayConfig vnPayConfig;

  /**
   * Create Payment Order
   *
   * @param user          user
   * @param amount        amount
   * @param paymentMethod paymentMethod
   * @return PaymentOrder
   */
  @Override
  public PaymentOrder createPaymentOrder(User user, Long amount, PaymentMethod paymentMethod) {
    PaymentOrder paymentOrder = new PaymentOrder();
    paymentOrder.setUser(user);
    paymentOrder.setAmount(amount);
    paymentOrder.setPaymentMethod(paymentMethod);
    paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.PENDING);
    return paymentRepository.save(paymentOrder);
  }

  @Override
  public PaymentOrder getPaymentOrderById(Long id) throws Exception {
    return paymentRepository.findById(id)
        .orElseThrow(() -> new Exception("Payment order not found"));
  }

  @Override
  public Boolean processPaymentOrder(PaymentOrder paymentOrder, HttpServletRequest request)
      throws UnsupportedEncodingException {
//    if (paymentOrder.getPaymentOrderStatus() == null) {
//      paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.PENDING);
//    }
//    if (paymentOrder.getPaymentOrderStatus().equals(PaymentOrderStatus.PENDING)) {
//      if (paymentOrder.getPaymentMethod().equals(PaymentMethod.VN_PAY)) {
//        int paymentStatus = orderReturn(request);
//        if (paymentStatus == 1) {
//          paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.SUCCESS);
//          paymentRepository.save(paymentOrder);
//          return true;
//        }
//        paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.FAILED);
//        paymentRepository.save(paymentOrder);
//        return false;
//      }
//      paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.SUCCESS);
//      paymentRepository.save(paymentOrder);
//      return true;
//    }
    return true;
  }

  @Override
  public PaymentResponse createZaloPayPayment(User user, Long amount) {
    return null;
  }

  @Override
  public String createVnPayPayment(User user, Long amount,
      String orderId, String urlReturn) throws Exception {
    String vnp_Version = "2.1.0";
    String vnp_Command = "pay";
    String orderType = "other";
    amount = amount * 100;
    String bankCode = "NCB";

    String vnp_TxnRef = vnPayConfig.getRandomNumber(8);
    String vnp_IpAddr = IpUtils.getIpAddress();

    String vnp_TmnCode = vnPayConfig.getVnp_TmnCode();

    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", vnp_Version);
    vnp_Params.put("vnp_Command", vnp_Command);
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_Amount", String.valueOf(amount));
    vnp_Params.put("vnp_CurrCode", "VND");

    if (bankCode != null && !bankCode.isEmpty()) {
      vnp_Params.put("vnp_BankCode", bankCode);
    }
    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", orderId);
    vnp_Params.put("vnp_OrderType", orderType);
    vnp_Params.put("vnp_Locale", "vn");
    urlReturn += vnPayConfig.getVnp_ReturnUrl();
    vnp_Params.put("vnp_ReturnUrl", urlReturn);
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

    cld.add(Calendar.MINUTE, 15);
    String vnp_ExpireDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

    List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext(); ) {
      String fieldName = itr.next();
      String fieldValue = vnp_Params.get(fieldName);
      if (fieldValue != null && fieldValue.length() > 0) {
        hashData.append(fieldName).append('=')
            .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
            .append('=')
            .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }
    String queryUrl = query.toString();
    String salt = vnPayConfig.getVnp_HashSecret();
    String vnp_SecureHash = vnPayConfig.hmacSHA512(salt, hashData.toString());
    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
    String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
    return paymentUrl;
  }

  @Override
  public boolean orderReturn(PaymentOrder paymentOrder, HttpServletRequest request)
      throws UnsupportedEncodingException {
    Map fields = new HashMap();
    for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
      String fieldName = URLEncoder.encode((String) params.nextElement(),
          StandardCharsets.US_ASCII.toString());
      String fieldValue = URLEncoder.encode(request.getParameter(fieldName),
          StandardCharsets.US_ASCII.toString());
      if ((fieldValue != null) && (fieldValue.length() > 0)) {
        fields.put(fieldName, fieldValue);
      }
    }

    String vnp_SecureHash = request.getParameter("vnp_SecureHash");
    if (fields.containsKey("vnp_SecureHashType")) {
      fields.remove("vnp_SecureHashType");
    }
    if (fields.containsKey("vnp_SecureHash")) {
      fields.remove("vnp_SecureHash");
    }
    String signValue = vnPayConfig.hashAllFields(fields);
    if (signValue.equals(vnp_SecureHash)) {
      if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
        paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.SUCCESS);
        paymentRepository.save(paymentOrder);
        return true;
      } else {
        paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.FAILED);
        paymentRepository.save(paymentOrder);
        return false;
      }
    } else {
      paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.FAILED);
      paymentRepository.save(paymentOrder);
      return true;
    }
  }
}


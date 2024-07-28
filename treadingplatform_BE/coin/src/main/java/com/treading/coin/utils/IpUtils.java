package com.treading.coin.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtils {

  public static String getIpAddress() {
    String ipAddress;
    try {
      InetAddress inetAddress = InetAddress.getLocalHost();
      ipAddress = inetAddress.getHostAddress();
    } catch (UnknownHostException e) {
      ipAddress = "Unknown IP:" + e.getMessage();
    }
    return ipAddress;
  }

}

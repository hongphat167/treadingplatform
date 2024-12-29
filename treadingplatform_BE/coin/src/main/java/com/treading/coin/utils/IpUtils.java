package com.treading.coin.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * The type Ip utils.
 */
public class IpUtils {

	/**
	 * Gets ip address.
	 *
	 * @return the ip address
	 */
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

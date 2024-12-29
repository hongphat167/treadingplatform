package com.treading.coin.service;

import com.treading.coin.controller.response.ApiResponse;

/**
 * The interface Chat bot service.
 */
public interface ChatBotService {
	/**
	 * Gets coin details.
	 *
	 * @param prompt the prompt
	 * @return the coin details
	 * @throws Exception the exception
	 */
	ApiResponse getCoinDetails(String prompt) throws Exception;
}

package com.treading.coin.service;

import com.treading.coin.controller.response.ApiResponse;

public interface ChatBotService {
    ApiResponse getCoinDetails(String prompt) throws Exception;
}

package com.treading.coin.controller;

import com.treading.coin.controller.request.PromptAiRequest;
import com.treading.coin.controller.response.ApiResponse;
import com.treading.coin.service.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ai/chat")
public class AiChatController {

    @Autowired
    private ChatBotService chatBotService;

    @PostMapping
    public ResponseEntity<ApiResponse> getCoinDetails(@RequestBody PromptAiRequest promptAiRequest) throws Exception {
        ApiResponse apiResponse = chatBotService.getCoinDetails(promptAiRequest.getPrompt());

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}

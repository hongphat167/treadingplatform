package com.treading.coin.controller;

import com.treading.coin.controller.request.PromptAiRequest;
import com.treading.coin.controller.response.ApiResponse;
import com.treading.coin.service.ChatBotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Ai chat controller.
 */
@RestController
@RequestMapping("ai/chat")
public class AiChatController {

	private final ChatBotService chatBotService;

	/**
	 * Instantiates a new Ai chat controller.
	 *
	 * @param chatBotService the chat bot service
	 */
	protected AiChatController(ChatBotService chatBotService) {
		this.chatBotService = chatBotService;
	}

	/**
	 * Gets coin details.
	 *
	 * @param promptAiRequest the prompt ai request
	 * @return the coin details
	 * @throws Exception the exception
	 */
	@PostMapping
	public ResponseEntity<ApiResponse> getCoinDetails(@RequestBody PromptAiRequest promptAiRequest) throws Exception {
		ApiResponse apiResponse = chatBotService.getCoinDetails(promptAiRequest.getPrompt());

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

}

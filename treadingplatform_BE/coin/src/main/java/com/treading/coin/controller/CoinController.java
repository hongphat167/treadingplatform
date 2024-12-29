package com.treading.coin.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treading.coin.model.Coin;
import com.treading.coin.service.CoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Coin controller.
 */
@RestController
@RequestMapping("/coins")
public class CoinController {


	private final CoinService coinService;
	private final ObjectMapper objectMapper;

	/**
	 * Instantiates a new Coin controller.
	 *
	 * @param coinService  the coin service
	 * @param objectMapper the object mapper
	 */
	protected CoinController(CoinService coinService, ObjectMapper objectMapper) {
		this.coinService = coinService;
		this.objectMapper = objectMapper;
	}

	/**
	 * Gets coin list.
	 *
	 * @param page the page
	 * @return the coin list
	 * @throws Exception the exception
	 */
	@GetMapping("/get-list")
	ResponseEntity<List<Coin>> getCoinList(@RequestParam(required = false, name = "page") int page) throws Exception {
		List<Coin> coins = coinService.getCoinList(page);
		return new ResponseEntity<>(coins, HttpStatus.OK);
	}

	/**
	 * Gets market chart.
	 *
	 * @param coinId the coin id
	 * @param days   the days
	 * @return the market chart
	 * @throws Exception the exception
	 */
	@GetMapping("/{coinId}/chart")
	ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinId,
	                                        @RequestParam("days") int days) throws Exception {
		String response = coinService.getMarketChart(coinId, days);
		JsonNode jsonNode = objectMapper.readTree(response);
		return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
	}

	/**
	 * Search coin response entity.
	 *
	 * @param keyword the keyword
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@GetMapping("/search")
	ResponseEntity<JsonNode> searchCoin(@RequestParam("key") String keyword) throws Exception {
		String coin = coinService.searchCoin(keyword);
		JsonNode jsonNode = objectMapper.readTree(coin);
		return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
	}

	/**
	 * Gets top 50 coin by market cap rank.
	 *
	 * @return the top 50 coin by market cap rank
	 * @throws Exception the exception
	 */
	@GetMapping("/top50")
	ResponseEntity<JsonNode> getTop50CoinByMarketCapRank() throws Exception {
		String coin = coinService.getTop50CoinsByMarketRank();
		JsonNode jsonNode = objectMapper.readTree(coin);
		return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
	}

	/**
	 * Gets trending coin.
	 *
	 * @return the trending coin
	 * @throws Exception the exception
	 */
	@GetMapping("/trending")
	ResponseEntity<JsonNode> getTrendingCoin() throws Exception {
		String coin = coinService.getTrendingCoins();
		JsonNode jsonNode = objectMapper.readTree(coin);
		return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
	}

	/**
	 * Gets coin detail.
	 *
	 * @param coinId the coin id
	 * @return the coin detail
	 * @throws Exception the exception
	 */
	@GetMapping("/detail/{coinId}")
	ResponseEntity<JsonNode> getCoinDetail(@PathVariable String coinId) throws Exception {
		String coin = coinService.getCoinDetails(coinId);
		JsonNode jsonNode = objectMapper.readTree(coin);
		return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
	}
}

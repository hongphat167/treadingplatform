package com.treading.coin.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treading.coin.model.Coin;
import com.treading.coin.service.CoinService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coins")
public class CoinController {

  @Autowired
  private CoinService coinService;
  @Autowired
  private ObjectMapper objectMapper;

  @GetMapping("/get-list")
  ResponseEntity<List<Coin>> getCoinList(@RequestParam("page") int page) throws Exception {
    List<Coin> coins = coinService.getCoinList(page);
    return new ResponseEntity<>(coins, HttpStatus.ACCEPTED);
  }

  @GetMapping("{coinId}/chart")
  ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinId,
      @RequestParam("days") int days) throws Exception {
    String response = coinService.getMarketChart(coinId, days);
    JsonNode jsonNode = objectMapper.readTree(response);
    return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
  }

  @GetMapping("/search")
  ResponseEntity<JsonNode> searchCoin(@RequestParam("key") String keyword) throws Exception {
    String coin = coinService.searchCoin(keyword);
    JsonNode jsonNode = objectMapper.readTree(coin);
    return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
  }

  @GetMapping("/top50")
  ResponseEntity<JsonNode> getTop50CoinByMarketCapRank() throws Exception {
    String coin = coinService.getTop50CoinsByMarketRank();
    JsonNode jsonNode = objectMapper.readTree(coin);
    return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
  }

  @GetMapping("/trending")
  ResponseEntity<JsonNode> getTrendingCoin() throws Exception {
    String coin = coinService.getTrendingCoins();
    JsonNode jsonNode = objectMapper.readTree(coin);
    return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
  }

  @GetMapping("/detail/{coinId}")
  ResponseEntity<JsonNode> getCoinDeatail(@PathVariable String coinId) throws Exception {
    String coin = coinService.getCoinDetails(coinId);
    JsonNode jsonNode = objectMapper.readTree(coin);
    return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
  }
}
package com.treading.coin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treading.coin.model.Coin;
import com.treading.coin.repository.CoinRepository;
import com.treading.coin.service.CoinService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CoinServiceImpl implements CoinService {

  @Autowired
  private CoinRepository coinRepository;
  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Get Coin List
   *
   * @param page page
   * @return coinList
   * @throws Exception e
   */
  @Override
  public List<Coin> getCoinList(int page) throws Exception {

    String url =
        "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page=" + page;

    RestTemplate restTemplate = new RestTemplate();
    try {
      HttpHeaders headers = new HttpHeaders();

      HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity,
          String.class);

      List<Coin> coinList = objectMapper.readValue(response.getBody(),
          new TypeReference<List<Coin>>() {
          });
      return coinList;

    } catch (HttpClientErrorException | HttpServerErrorException | JsonProcessingException e) {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * Get Market Chart
   *
   * @param coinId coinId
   * @param days   days
   * @return response
   * @throws Exception e
   */
  @Override
  public String getMarketChart(String coinId, int days) throws Exception {
    String url =
        "https://api.coingecko.com/api/v3/coins/" + coinId + "/market_chart?vs_currency=usd&days="
            + days;

    RestTemplate restTemplate = new RestTemplate();
    try {
      HttpHeaders headers = new HttpHeaders();

      HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity,
          String.class);

      return response.getBody();

    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * Get Coin Details
   *
   * @param coinId coinId
   * @return response
   * @throws Exception e
   */
  @Override
  public String getCoinDetails(String coinId) throws Exception {
    String url = "https://api.coingecko.com/api/v3/coins/" + coinId;

    RestTemplate restTemplate = new RestTemplate();
    try {
      HttpHeaders headers = new HttpHeaders();

      HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity,
          String.class);

      JsonNode jsonNode = objectMapper.readTree(response.getBody());
      Coin coin = new Coin();
      if (jsonNode.has("id")) {
        coin.setId(jsonNode.get("id").asText());
      }
      if (jsonNode.has("name")) {
        coin.setName(jsonNode.get("name").asText());
      }
      if (jsonNode.has("symbol")) {
        coin.setSymbol(jsonNode.get("symbol").asText());
      }
      if (jsonNode.has("image") && jsonNode.get("image").has("large")) {
        coin.setImage(jsonNode.get("image").get("large").asText());
      }

      JsonNode marketData = jsonNode.get("market_data");
      if (marketData != null) {
        if (marketData.has("current_price") && marketData.get("current_price").has("usd")) {
          coin.setCurrentPrice(
              BigDecimal.valueOf(marketData.get("current_price").get("usd").asDouble()));
        }
        if (marketData.has("market_cap") && marketData.get("market_cap").has("usd")) {
          coin.setMarketCap(BigDecimal.valueOf(marketData.get("market_cap").get("usd").asLong()));
        }
        if (marketData.has("market_cap_rank")) {
          coin.setMarketCapRank(marketData.get("market_cap_rank").asInt());
        }
        if (marketData.has("total_volume") && marketData.get("total_volume").has("usd")) {
          coin.setTotalVolume(
              BigDecimal.valueOf(marketData.get("total_volume").get("usd").asDouble()));
        }
        if (marketData.has("high_24h") && marketData.get("high_24h").has("usd")) {
          coin.setHigh24h(BigDecimal.valueOf(marketData.get("high_24h").get("usd").asDouble()));
        }
        if (marketData.has("low_24h") && marketData.get("low_24h").has("usd")) {
          coin.setLow24h(BigDecimal.valueOf(marketData.get("low_24h").get("usd").asDouble()));
        }
        if (marketData.has("price_change_24h")) {
          coin.setPriceChange24h(marketData.get("price_change_24h").asDouble());
        }
        if (marketData.has("price_change_percentage_24h")) {
          coin.setPriceChangePercentage24h(
              marketData.get("price_change_percentage_24h").asDouble());
        }
        if (marketData.has("market_cap_change_24h")) {
          coin.setMarketCapChange24h(
              BigDecimal.valueOf(marketData.get("market_cap_change_24h").asLong()));
        }
        if (marketData.has("market_cap_change_percentage_24h")) {
          coin.setMarketCapChangePercentage24h(
              BigDecimal.valueOf(marketData.get("market_cap_change_percentage_24h").asDouble()));
        }
        if (marketData.has("total_supply")) {
          coin.setTotalSupply(BigDecimal.valueOf(marketData.get("total_supply").asLong()));
        }
      }

      coinRepository.save(coin);
      return response.getBody();

    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public Coin findById(String coinId) throws Exception {
    Optional<Coin> coinOptional = coinRepository.findById(coinId);
    if (coinOptional.isEmpty()) {
      throw new Exception("Coin not found");
    }
    return coinOptional.get();
  }

  @Override
  public String searchCoin(String keyword) throws Exception {
    String url =
        "https://api.coingecko.com/api/v3/search?query=" + keyword;

    RestTemplate restTemplate = new RestTemplate();
    try {
      HttpHeaders headers = new HttpHeaders();

      HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity,
          String.class);

      return response.getBody();

    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public String getTop50CoinsByMarketRank() throws Exception {
    String url =
        "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=50&page=1";

    RestTemplate restTemplate = new RestTemplate();
    try {
      HttpHeaders headers = new HttpHeaders();

      HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity,
          String.class);

      return response.getBody();

    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public String getTrendingCoins() throws Exception {
    String url =
        "https://api.coingecko.com/api/v3/search/trending";

    RestTemplate restTemplate = new RestTemplate();
    try {
      HttpHeaders headers = new HttpHeaders();

      HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity,
          String.class);

      return response.getBody();

    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new Exception(e.getMessage());
    }
  }
}

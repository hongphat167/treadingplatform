package com.treading.coin.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treading.coin.controller.response.ApiResponse;
import com.treading.coin.controller.response.FunctionResponse;
import com.treading.coin.model.Coin;
import com.treading.coin.service.ChatBotService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@Slf4j
public class ChatBotServiceImpl implements ChatBotService {

	@Autowired
	private ObjectMapper objectMapper;

	private final String apiGeminiKey = "AIzaSyDnEd8Vq698HHdHqwdbgJk1spybNjrfPwk";
	private final String geminiApiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + apiGeminiKey;

	private final RestTemplate restTemplate = new RestTemplate();


	public Coin getCoin(String currencyName) throws Exception {
		String url = "https://api.coingecko.com/api/v3/coins/" + currencyName;
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
				if (marketData.has("max_supply")) {
					coin.setMaxSupply(BigDecimal.valueOf(marketData.get("max_supply").asLong()));
				}
				if (marketData.has("ath")) {
					coin.setAth(BigDecimal.valueOf(marketData.get("ath").asLong()));
				}
				if (marketData.has("ath_change_percentage")) {
					coin.setAtlChangePercentage(BigDecimal.valueOf(marketData.get("ath_change_percentage").asLong()));
				}
				if (marketData.has("ath_date")) {
					coin.setAthDate(marketData.get("ath_date").asText());
				}
				if (marketData.has("atl")) {
					coin.setAtl(BigDecimal.valueOf(marketData.get("atl").asLong()));
				}
				if (marketData.has("atl_change_percentage")) {
					coin.setAtlChangePercentage(
							BigDecimal.valueOf(marketData.get("atl_change_percentage").asLong())
					);
				}
				if (marketData.has("atl_date")) {
					coin.setAtlDate(marketData.get("atl_date").asText());
				}
				if (marketData.has("roi")) {
					coin.setRoi(marketData.get("roi").asText());
				}
				if (marketData.has("last_updated")) {
					coin.setLastUpdated(marketData.get("last_updated").asText());
				}
			}
			log.info("List of coin {}", coin);
			return coin;

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * functionResponse
	 *
	 * @param prompt prompt
	 * @return FunctionResponse
	 * @throws Exception e
	 */
	public FunctionResponse functionResponse(String prompt) throws Exception {
		// Tạo JSON yêu cầu
		JSONObject jsonObject = new JSONObject()
				.put("contents", new JSONArray()
						.put(new JSONObject()
								.put("parts", new JSONArray()
										.put(new JSONObject()
												.put("text", prompt)
										)
								)
						)
				)
				.put("tools", new JSONArray()
						.put(new JSONObject()
								.put("functionDeclarations", new JSONArray()
										.put(new JSONObject()
												.put("name", "getCoinDetails")
												.put("description", "Get crypto currency data form given currency object")
												.put("parameters", new JSONObject()
														.put("type", "OBJECT")
														.put("properties", new JSONObject()
																.put("currencyName", new JSONObject()
																		.put("type", "STRING")
																		.put("description", "The currency Name, id, symbol.")
																)
																.put("currencyData", new JSONObject()
																		.put("type", "STRING")
																		.put("description", "The currency data id, symbol, name, image, current_price, " +
																				"market_cap, market_cap_rank, fully_diluted_valuation, total_volume, high_24h, " +
																				"low_24h, price_change_24h, price_change_percentage_24h, market_cap_change_24h, " +
																				"market_cap_change_percentage_24h, circulating_supply, total_supply, max_supply, " +
																				"ath, ath_change_percentage, ath_date, atl, atl_change_percentage, atl_date, last_updated."
																		)
																)
														)
														.put("required", new JSONArray()
																.put("currencyName")
																.put("currencyData")
														)
												)
										)
								)
						)
				);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> requestEntity = new HttpEntity<>(jsonObject.toString(), headers);
		log.info("Request entity {}", requestEntity);

		try {
			// Gửi yêu cầu đến API
			ResponseEntity<String> response = restTemplate.postForEntity(geminiApiUrl, requestEntity, String.class);

			// Kiểm tra xem phản hồi có thành công không
			if (response.getStatusCode() != HttpStatus.OK) {
				throw new Exception("Failed to get response from API. Status: " + response.getStatusCode());
			}

			String responseBody = response.getBody();
			JSONObject functionCall = getJsonObject(responseBody);

			// Trích xuất chi tiết từ function call
			String functionName = functionCall.getString("name");
			log.info("functionName {}", functionName);

			JSONObject args = functionCall.optJSONObject("args");
			if (args == null) {
				log.error("Arguments not found in function call");
				throw new Exception("Arguments not found in function call");
			}

			// Trích xuất các trường cần thiết
			String currencyName = args.optString("currencyName", "").toLowerCase();
			String currencyData = args.optString("currencyData", "").toLowerCase();

			if (currencyName.isEmpty() || currencyData.isEmpty()) {
				throw new Exception("Required fields 'currencyName' or 'currencyData' are missing");
			}

			log.info("responseBody {}", responseBody);

			// Tạo đối tượng FunctionResponse
			FunctionResponse functionResponse = new FunctionResponse();
			functionResponse.setFunctionName(functionName);
			functionResponse.setCurrencyName(currencyName);
			functionResponse.setCurrencyData(currencyData);

			return functionResponse;

		} catch (Exception e) {
			log.error("Error in functionResponse: ", e);
			throw new Exception("Error processing function response: " + e.getMessage(), e);
		}
	}

	/**
	 * getJsonObject
	 *
	 * @param responseBody responseBody
	 * @return JSONObject
	 * @throws Exception e
	 */
	private static JSONObject getJsonObject(String responseBody) throws Exception {
		if (responseBody == null) {
			throw new Exception("Response body is null");
		}

		JSONArray parts = getObjects(responseBody);
		if (parts == null || parts.isEmpty()) {
			throw new Exception("Parts array is empty in content");
		}

		JSONObject part = parts.getJSONObject(0);
		JSONObject functionCall = part.optJSONObject("functionCall");
		if (functionCall == null) {
			throw new Exception("Function call not found in part");
		}
		return functionCall;
	}

	private static JSONArray getObjects(String responseBody) throws Exception {
		JSONObject responseObject = new JSONObject(responseBody);

		// Kiểm tra nếu mảng "candidates" tồn tại và không rỗng
		if (!responseObject.has("candidates") || responseObject.getJSONArray("candidates").isEmpty()) {
			throw new Exception("No candidates found in the response");
		}

		JSONArray candidates = responseObject.getJSONArray("candidates");
		JSONObject candidate = candidates.getJSONObject(0);

		JSONObject content = candidate.optJSONObject("content");
		if (content == null) {
			throw new Exception("Content not found in candidate");
		}

		return content.optJSONArray("parts");
	}


	@Override
	public ApiResponse getCoinDetails(String prompt) throws Exception {

		FunctionResponse rp = functionResponse(prompt);
		Coin apiCoinResponse = getCoin(rp.getCurrencyName());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String body = new JSONObject()
				.put("contents", new JSONArray()
						.put(new JSONObject()
								.put("role", "user")
								.put("parts", new JSONArray()
										.put(new JSONObject()
												.put("text", prompt)
										)
								)
						)
						.put(new JSONObject()
								.put("role", "model")
								.put("parts", new JSONArray()
										.put(new JSONObject()
												.put("functionCall", new JSONObject()
														.put("name", "getCoinDetails")
														.put("args", new JSONObject()
																.put("currencyName", rp.getCurrencyName())
																.put("currencyData", rp.getCurrencyData())
														)
												)
										)
								)
						)
						.put(new JSONObject()
								.put("role", "function")
								.put("parts", new JSONArray()
										.put(new JSONObject()
												.put("functionResponse", new JSONObject()
														.put("name", "getCoinDetails")
														.put("response", new JSONObject()
																.put("name", "getCoinDetails")
																.put("content", apiCoinResponse)
														)
												)
										)
								)
						)
				)
				.put("tools", new JSONArray()
						.put(new JSONObject()
								.put("functionDeclarations", new JSONArray()
										.put(new JSONObject()
												.put("name", "getCoinDetails")
												.put("description", "Get crypto currency data from given currency object.")
												.put("parameters", new JSONObject()
														.put("type", "OBJECT")
														.put("properties", new JSONObject()
																.put("currencyName", new JSONObject()
																		.put("type", "STRING")
																		.put("description",
																				"The currency Name, " +
																						"id, " +
																						"symbol.")
																)
																.put("currencyData", new JSONObject()
																		.put("type", "STRING")
																		.put("description",
																				"The currency data id, " +
																						"symbol, current price, " +
																						"image, " +
																						"market cap rank" +
																						"market cap extra...")
																)
														)
														.put("required", new JSONArray()
																.put("currencyName")
																.put("currencyData")
														)
												)
										)
								)
						)
				)
				.toString();
		HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(geminiApiUrl, requestEntity, String.class);
		String responseBody = responseEntity.getBody();
		JSONObject jsonObject = new JSONObject(responseBody);

		JSONArray candidates = jsonObject.getJSONArray("candidates");
		JSONObject content = candidates.getJSONObject(0).getJSONObject("content");
		JSONArray parts = content.getJSONArray("parts");
		String text = parts.getJSONObject(0).getString("text");

		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage(text);
		return apiResponse;
	}
}

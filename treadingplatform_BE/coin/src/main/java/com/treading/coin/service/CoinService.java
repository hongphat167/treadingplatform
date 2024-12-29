package com.treading.coin.service;

import com.treading.coin.model.Coin;

import java.util.List;


/**
 * The interface Coin service.
 */
public interface CoinService {

	/**
	 * Gets coin list.
	 *
	 * @param page the page
	 * @return the coin list
	 * @throws Exception the exception
	 */
	List<Coin> getCoinList(int page) throws Exception;

	/**
	 * Gets market chart.
	 *
	 * @param coinId the coin id
	 * @param days   the days
	 * @return the market chart
	 * @throws Exception the exception
	 */
	String getMarketChart(String coinId, int days) throws Exception;

	/**
	 * Gets coin details.
	 *
	 * @param coinId the coin id
	 * @return the coin details
	 * @throws Exception the exception
	 */
	String getCoinDetails(String coinId) throws Exception;

	/**
	 * Find by id coin.
	 *
	 * @param coinId the coin id
	 * @return the coin
	 * @throws Exception the exception
	 */
	Coin findById(String coinId) throws Exception;

	/**
	 * Search coin string.
	 *
	 * @param keyword the keyword
	 * @return the string
	 * @throws Exception the exception
	 */
	String searchCoin(String keyword) throws Exception;

	/**
	 * Gets top 50 coins by market rank.
	 *
	 * @return the top 50 coins by market rank
	 * @throws Exception the exception
	 */
	String getTop50CoinsByMarketRank() throws Exception;

	/**
	 * Gets trending coins.
	 *
	 * @return the trending coins
	 * @throws Exception the exception
	 */
	String getTrendingCoins() throws Exception;
}

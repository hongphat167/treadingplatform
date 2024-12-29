package com.treading.coin.service;

import com.treading.coin.model.Asset;
import com.treading.coin.model.Coin;
import com.treading.coin.model.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * The interface Asset service.
 */
public interface AssetService {

	/**
	 * Create asset.
	 *
	 * @param user     the user
	 * @param coin     the coin
	 * @param quantity the quantity
	 */
	void createAsset(User user, Coin coin, BigDecimal quantity);

	/**
	 * Gets asset by id.
	 *
	 * @param assetId the asset id
	 * @return the asset by id
	 * @throws Exception the exception
	 */
	Asset getAssetById(Long assetId) throws Exception;

	/**
	 * Gets asset by user id and id.
	 *
	 * @param userId  the user id
	 * @param assetId the asset id
	 * @return the asset by user id and id
	 */
	Asset getAssetByUserIdAndId(Long userId, Long assetId);

	/**
	 * Gets users assets.
	 *
	 * @param userId the user id
	 * @return the users assets
	 */
	List<Asset> getUsersAssets(Long userId);

	/**
	 * Update asset asset.
	 *
	 * @param assetId  the asset id
	 * @param quantity the quantity
	 * @return the asset
	 * @throws Exception the exception
	 */
	Asset updateAsset(Long assetId, BigDecimal quantity) throws Exception;

	/**
	 * Find asset by user id and coin id asset.
	 *
	 * @param userId the user id
	 * @param coinId the coin id
	 * @return the asset
	 */
	Asset findAssetByUserIdAndCoinId(Long userId, String coinId);

	/**
	 * Delete asset.
	 *
	 * @param assetId the asset id
	 */
	void deleteAsset(Long assetId);
}

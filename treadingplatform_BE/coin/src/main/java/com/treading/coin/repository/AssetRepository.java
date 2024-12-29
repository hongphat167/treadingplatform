package com.treading.coin.repository;

import com.treading.coin.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Asset repository.
 */
public interface AssetRepository extends JpaRepository<Asset, Long> {

	/**
	 * Find by user id list.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	List<Asset> findByUserId(Long userId);

	/**
	 * Find by user id and coin id asset.
	 *
	 * @param userId the user id
	 * @param coinId the coin id
	 * @return the asset
	 */
	Asset findByUserIdAndCoinId(Long userId, String coinId);

	/**
	 * Gets asset by user id and id.
	 *
	 * @param userId  the user id
	 * @param assetId the asset id
	 * @return the asset by user id and id
	 */
	Asset getAssetByUserIdAndId(Long userId, Long assetId);
}

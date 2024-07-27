package com.treading.coin.repository;

import com.treading.coin.model.Asset;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository  extends JpaRepository<Asset, Long> {

  /**
   * Find By User Id
   *
   * @param userId userId
   * @return List<Asset>
   */
	List<Asset> findByUserId(Long userId);

  /**
   * Find By User Id And Coin Id
   *
   * @param userId userId
   * @param coinId coinId
   * @return Asset
   */
  Asset findByUserIdAndCoinId(Long userId, String coinId);

  /**
   * Get Asset By User Id And Id
   *
   * @param userId userid
   * @param assetId assetId
   * @return Asset
   */
  Asset getAssetByUserIdAndId(Long userId, Long assetId);


}

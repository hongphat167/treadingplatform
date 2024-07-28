package com.treading.coin.service;

import com.treading.coin.model.Asset;
import com.treading.coin.model.Coin;
import com.treading.coin.model.User;
import java.math.BigDecimal;
import java.util.List;

public interface AssetService {

  /**
   * Create Asset
   *
   * @param user     user
   * @param coin     coin
   * @param quantity quantity
   */
  void createAsset(User user, Coin coin, BigDecimal quantity);

  /**
   * Get Asset By Id
   *
   * @param assetId assetId
   * @return Asset
   * @throws Exception e
   */
  Asset getAssetById(Long assetId) throws Exception;

  /**
   * Get Asset By User Id And Id
   *
   * @param userId  userId
   * @param assetId assetId
   * @return Asset
   */
  Asset getAssetByUserIdAndId(Long userId, Long assetId);

  /**
   * Get Users Assets
   *
   * @param userId userId
   * @return List<Asset>
   */
  List<Asset> getUsersAssets(Long userId);

  /**
   * Update Asset
   *
   * @param assetId  assetId
   * @param quantity quantity
   * @return Asset
   * @throws Exception e
   */
  Asset updateAsset(Long assetId, BigDecimal quantity) throws Exception;

  /**
   * Find Asset By User Id And Coin Id
   *
   * @param userId userId
   * @param coinId coinId
   * @return Asset
   */
  Asset findAssetByUserIdAndCoinId(Long userId, String coinId);

  /**
   * Delete Asset
   *
   * @param assetId assetId
   */
  void deleteAsset(Long assetId);
}

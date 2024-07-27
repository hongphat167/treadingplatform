package com.treading.coin.service.impl;

import com.treading.coin.model.Asset;
import com.treading.coin.model.Coin;
import com.treading.coin.model.User;
import com.treading.coin.repository.AssetRepository;
import com.treading.coin.service.AssetService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetServiceImpl implements AssetService {

  @Autowired
  private AssetRepository assetRepository;

  /**
   * Create Asset
   *
   * @param user     user
   * @param coin     coin
   * @param quantity quantity
   */
  @Override
  public void createAsset(User user, Coin coin, BigDecimal quantity) {

    Asset asset = new Asset();
    asset.setUser(user);
    asset.setCoin(coin);
    asset.setQuantity(quantity);
    asset.setBuyPrice(coin.getCurrentPrice());

    assetRepository.save(asset);
  }

  /**
   * Get Asset By Id
   *
   * @param assetId assetId
   * @return Asset
   * @throws Exception e
   */
  @Override
  public Asset getAssetById(Long assetId) throws Exception {

    return assetRepository.findById(assetId).orElseThrow(() -> new Exception("asset not found"));
  }

  /**
   * Get Asset By User Id And Id
   *
   * @param userId  userId
   * @param assetId assetId
   * @return Asset
   */
  @Override
  public Asset getAssetByUserIdAndId(Long userId, Long assetId) {

    return assetRepository.getAssetByUserIdAndId(userId, assetId);
  }

  /**
   * Get Users Assets
   *
   * @param userId userId
   * @return List<Asset>
   */
  @Override
  public List<Asset> getUsersAssets(Long userId) {

    return assetRepository.findByUserId(userId);
  }

  /**
   * Update Asset
   *
   * @param assetId  assetId
   * @param quantity quantity
   * @return Asset
   * @throws Exception e
   */
  @Override
  public Asset updateAsset(Long assetId, BigDecimal quantity) throws Exception {
    Asset oldAsset = getAssetById(assetId);
    oldAsset.setQuantity(quantity.add(oldAsset.getQuantity()));
    return assetRepository.save(oldAsset);
  }

  /**
   * Find Asset By User Id And Coin Id
   *
   * @param userId userId
   * @param coinId coinId
   * @return Asset
   */
  @Override
  public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {

    return assetRepository.findByUserIdAndCoinId(userId, coinId);
  }

  /**
   * Delete Asset
   *
   * @param assetId assetId
   */
  @Override
  public void deleteAsset(Long assetId) {
    assetRepository.deleteById(assetId);
  }
}

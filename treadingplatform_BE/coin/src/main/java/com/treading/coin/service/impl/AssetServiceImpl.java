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

  @Override
  public Asset createAsset(User user, Coin coin, BigDecimal quantity) {

    Asset asset = new Asset();
    asset.setUser(user);
    asset.setCoin(coin);
    asset.setQuantity(quantity);
    asset.setBuyPrice(coin.getCurrentPrice());
    return assetRepository.save(asset);
  }

  @Override
  public Asset getAssetById(Long assetId) throws Exception {

    return assetRepository.findById(assetId).orElseThrow(() -> new Exception("asset not found"));
  }

  @Override
  public Asset getAssetByUserIdAndId(Long userId, Long assetId) {

    return null;
  }

  @Override
  public List<Asset> getUsersAssets(Long userId) {

    return assetRepository.findByUserId(userId);
  }

  @Override
  public Asset updateAsset(Long assetId, BigDecimal quantity) throws Exception {
    Asset oldAsset = getAssetById(assetId);
    oldAsset.setQuantity(quantity.add(oldAsset.getQuantity()));
    return assetRepository.save(oldAsset);
  }

  @Override
  public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {

    return assetRepository.findByUserIdAndCoinId(userId, coinId);
  }

  @Override
  public void deleteAsset(Long assetId) {
    assetRepository.deleteById(assetId);
  }
}

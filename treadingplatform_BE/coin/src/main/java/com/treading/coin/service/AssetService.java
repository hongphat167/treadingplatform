package com.treading.coin.service;

import com.treading.coin.model.Asset;
import com.treading.coin.model.Coin;
import com.treading.coin.model.User;
import java.math.BigDecimal;
import java.util.List;

public interface AssetService {
	Asset createAsset(User user, Coin coin, BigDecimal quantity);

  Asset getAssetById(Long assetId) throws Exception;

  Asset getAssetByUserIdAndId(Long userId, Long assetId);

  List<Asset> getUsersAssets(Long userId);

  Asset updateAsset(Long assetId, BigDecimal quantity) throws Exception;

  Asset findAssetByUserIdAndCoinId(Long userId, String coinId);

  void deleteAsset(Long assetId);
}

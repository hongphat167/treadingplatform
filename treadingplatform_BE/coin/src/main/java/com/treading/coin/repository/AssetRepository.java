package com.treading.coin.repository;

import com.treading.coin.model.Asset;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository  extends JpaRepository<Asset, Long> {

	List<Asset> findByUserId(Long userId);

  Asset findByUserIdAndCoinId(Long userId, String coinId);


}

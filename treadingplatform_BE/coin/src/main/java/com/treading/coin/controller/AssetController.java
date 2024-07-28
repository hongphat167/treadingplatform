package com.treading.coin.controller;

import com.treading.coin.model.Asset;
import com.treading.coin.model.User;
import com.treading.coin.service.AssetService;
import com.treading.coin.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/assets")
public class AssetController {

  @Autowired
  private AssetService assetService;
  @Autowired
  private UserService userService;

  @PostMapping("/{assetId}")
  public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception {
    Asset asset = assetService.getAssetById(assetId);

    return new ResponseEntity<>(asset, HttpStatus.OK);
  }

  @GetMapping("/coin/{coinId}/user")
  public ResponseEntity<Asset> getAssetByUserIdAndCoinId(@PathVariable String coinId,
      @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserProfileByJwt(jwt);

    Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coinId);

    return new ResponseEntity<>(asset, HttpStatus.OK);
  }

  @GetMapping("/get-list-asset")
  public ResponseEntity<List<Asset>> getAssetsForUser(@RequestHeader("Authorization") String jwt)
      throws Exception {

    User user = userService.findUserProfileByJwt(jwt);
    List<Asset> assetList = assetService.getUsersAssets(user.getId());

    return new ResponseEntity<>(assetList, HttpStatus.OK);
  }
}

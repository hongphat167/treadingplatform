package com.treading.coin.controller;

import com.treading.coin.model.Asset;
import com.treading.coin.model.User;
import com.treading.coin.service.AssetService;
import com.treading.coin.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Asset controller.
 */
@RestController
@RequestMapping("api/assets")
public class AssetController {


	private final AssetService assetService;

	private final UserService userService;

	/**
	 * Instantiates a new Asset controller.
	 *
	 * @param assetService the asset service
	 * @param userService  the user service
	 */
	protected AssetController(AssetService assetService, UserService userService) {
		this.assetService = assetService;
		this.userService = userService;
	}

	/**
	 * Gets asset by id.
	 *
	 * @param assetId the asset id
	 * @return the asset by id
	 * @throws Exception the exception
	 */
	@PostMapping("/{assetId}")
	public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception {
		Asset asset = assetService.getAssetById(assetId);

		return new ResponseEntity<>(asset, HttpStatus.OK);
	}

	/**
	 * Gets asset by user id and coin id.
	 *
	 * @param coinId the coin id
	 * @param jwt    the jwt
	 * @return the asset by user id and coin id
	 * @throws Exception the exception
	 */
	@GetMapping("/coin/{coinId}/user")
	public ResponseEntity<Asset> getAssetByUserIdAndCoinId(@PathVariable String coinId,
	                                                       @RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserProfileByJwt(jwt);

		Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coinId);

		return new ResponseEntity<>(asset, HttpStatus.OK);
	}

	/**
	 * Gets assets for user.
	 *
	 * @param jwt the jwt
	 * @return the assets for user
	 * @throws Exception the exception
	 */
	@GetMapping("/get-list-asset")
	public ResponseEntity<List<Asset>> getAssetsForUser(@RequestHeader("Authorization") String jwt)
			throws Exception {

		User user = userService.findUserProfileByJwt(jwt);
		List<Asset> assetList = assetService.getUsersAssets(user.getId());

		return new ResponseEntity<>(assetList, HttpStatus.OK);
	}
}

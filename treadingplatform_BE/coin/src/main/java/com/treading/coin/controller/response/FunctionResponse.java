package com.treading.coin.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Function response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FunctionResponse {
	private String currencyName;
	private String functionName;
	private String currencyData;
}

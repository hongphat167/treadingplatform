package com.treading.coin.controller.response;

import lombok.Data;

@Data
public class FunctionResponse {
    /**
     * currencyName
     */
    private String currencyName;
    /**
     * functionName
     */
    private String functionName;
    /**
     * currencyData
     */
    private String currencyData;
}

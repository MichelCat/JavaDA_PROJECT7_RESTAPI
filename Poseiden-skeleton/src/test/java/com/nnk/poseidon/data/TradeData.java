package com.nnk.poseidon.data;

import com.nnk.poseidon.domain.Trade;

public class TradeData {

    public static Trade getTradeSource() {
        return Trade.builder()
                .account("Trade Account")
                .type("Type")
                .buyQuantity(0d)
                .build();
    }
}

package com.nnk.springboot.data;

import com.nnk.springboot.domain.Trade;

public class TradeData {

    public static Trade getTradeSource() {
        return Trade.builder()
                .account("Trade Account")
                .type("Type")
                .buyQuantity(0d)
                .build();
    }
}

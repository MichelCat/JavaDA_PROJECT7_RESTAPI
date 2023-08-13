package com.nnk.poseidon.data;

import com.nnk.poseidon.domain.Trade;
import com.nnk.poseidon.mapper.MultiValueMapMapper;
import org.springframework.util.MultiValueMap;

/**
 * TradeData is the class containing the Trade test data
 *
 * @author MC
 * @version 1.0
 */
public class TradeData {

    public static Trade getTradeSource() {
        return Trade.builder()
                .account("Trade Account")
                .type("Type")
                .buyQuantity(10d)
                .sellQuantity(11d)
                .buyPrice(12d)
                .sellPrice(13d)
                .benchmark("Benchmark")
                .tradeDate(GlobalData.CURRENT_TIMESTAMP)
                .security("Security")
                .status("Status")
                .trader("Trader")
                .book("Book")
                .creationName("CreationName")
                .creationDate(GlobalData.CURRENT_TIMESTAMP)
                .revisionName("RevisionName")
                .revisionDate(GlobalData.CURRENT_TIMESTAMP)
                .dealName("DealName")
                .dealType("DealType")
                .sourceListId("SourceListId")
                .side("Side")
                .build();
    }

    public static Trade getTradeSave() {
        Trade trade = getTradeSource();
        trade.setTradeId(1);
        return trade;
    }

    public static MultiValueMap<String, String> getTradeSourceController() {
        Trade trade = Trade.builder()
                        .account("Trade Account")
                        .type("Type")
                        .buyQuantity(10d)
                        .build();
        return MultiValueMapMapper.convert(trade);
    }

    public static MultiValueMap<String, String> getTradeSaveController() {
        Trade trade = Trade.builder()
                        .tradeId(1)
                        .account("Trade Account")
                        .type("Type")
                        .buyQuantity(10d)
                        .build();
        return MultiValueMapMapper.convert(trade);
    }

    public final static String scriptCreateTrade = "/data/createTrade.sql";
}

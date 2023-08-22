package com.nnk.poseidon.business;

import com.nnk.poseidon.data.TradeData;
import com.nnk.poseidon.model.Trade;
import com.nnk.poseidon.exception.MyExceptionBadRequestException;
import com.nnk.poseidon.exception.MyExceptionNotFoundException;
import com.nnk.poseidon.data.GlobalData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * TradeBusinessIT is a class of integration tests on Trades service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class TradeBusinessIT {

    @Autowired
    private TradeBusiness tradeBusiness;

    private Trade tradeSource;
    private Trade tradeSave;
    private Integer tradeId;


    @BeforeEach
    public void setUpBefore() {
        tradeSource = TradeData.getTradeSource();
        tradeSave = TradeData.getTradeSave();

        tradeId = tradeSave.getTradeId();
    }

    // -----------------------------------------------------------------------------------------------
    // getTradesList method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    void getTradesList_findAllNormal() {
        // GIVEN
        // WHEN
        List<Trade> result = tradeBusiness.getTradesList();
        // THEN
        assertThat(result).hasSize(1)
                            .contains(tradeSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void getTradesList_findAllEmpty() {
        // GIVEN
        // WHEN
        List<Trade> result = tradeBusiness.getTradesList();
        // THEN
        assertThat(result).isEmpty();
    }

    // -----------------------------------------------------------------------------------------------
    // createTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void createTrade_tradeNotExist() {
        // GIVEN
        // WHEN
        Trade result = tradeBusiness.createTrade(tradeSource);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("creationDate")
                .isEqualTo(tradeSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    void createTrade_tradeExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> tradeBusiness.createTrade(tradeSave));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // getTradeById method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    void getTradeById_tradeExist() {
        // GIVEN
        // WHEN
        assertThat(tradeBusiness.getTradeById(tradeId)).isEqualTo(tradeSave);
        // THEN
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void getTradeById_tradeNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.getTradeById(tradeId));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // updateTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    void updateTrade_tradeExist() {
        // GIVEN
        // WHEN
        Trade result = tradeBusiness.updateTrade(tradeId, tradeSave);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("revisionDate")
                .isEqualTo(tradeSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void updateTrade_tradeNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.updateTrade(tradeId, tradeSave));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // deleteTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    void deleteTrade_tradeExist() {
        // GIVEN
        // WHEN
        tradeBusiness.deleteTrade(tradeId);
        // THEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.getTradeById(tradeId));
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void deleteTrade_tradeNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.deleteTrade(tradeId));
        // THEN
    }
}

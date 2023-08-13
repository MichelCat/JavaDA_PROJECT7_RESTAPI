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
public class TradeBusinessIT {

    @Autowired
    private TradeBusiness tradeBusiness;

    private Trade tradeSource;
    private Trade tradeSave;


    @BeforeEach
    public void setUpBefore() {
        tradeSource = TradeData.getTradeSource();
        tradeSave = TradeData.getTradeSave();
    }

    // -----------------------------------------------------------------------------------------------
    // getTradesList method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    public void getTradesList_findAllNormal() {
        // GIVEN
        // WHEN
        List<Trade> result = tradeBusiness.getTradesList();
        // THEN
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(tradeSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void getTradesList_findAllEmpty() {
        // GIVEN
        // WHEN
        List<Trade> result = tradeBusiness.getTradesList();
        // THEN
        assertThat(result.size()).isEqualTo(0);
    }

    // -----------------------------------------------------------------------------------------------
    // createTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void createTrade_tradeNotExist() {
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
    public void createTrade_tradeExist() {
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
    public void getTradeById_tradeExist() {
        // GIVEN
        // WHEN
        assertThat(tradeBusiness.getTradeById(tradeSave.getTradeId())).isEqualTo(tradeSave);
        // THEN
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void getTradeById_tradeNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.getTradeById(tradeSave.getTradeId()));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // updateTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    public void updateTrade_tradeExist() {
        // GIVEN
        // WHEN
        Trade result = tradeBusiness.updateTrade(tradeSave.getTradeId(), tradeSave);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("revisionDate")
                .isEqualTo(tradeSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void updateTrade_tradeNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.updateTrade(tradeSave.getTradeId(), tradeSave));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // deleteTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    public void deleteTrade_tradeExist() {
        // GIVEN
        // WHEN
        tradeBusiness.deleteTrade(tradeSave.getTradeId());
        // THEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.getTradeById(tradeSave.getTradeId()));
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void deleteTrade_tradeNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.deleteTrade(tradeSave.getTradeId()));
        // THEN
    }
}

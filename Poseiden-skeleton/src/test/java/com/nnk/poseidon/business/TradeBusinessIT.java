package com.nnk.poseidon.business;

import com.nnk.poseidon.data.TradeData;
import com.nnk.poseidon.model.Trade;
import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.data.GlobalData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
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
    @Autowired
    private MessageSource messageSource;

    private TestMessageSource testMessageSource;
    private Trade tradeSource;
    private Trade tradeSave;
    private Integer tradeId;


    @BeforeEach
    public void setUpBefore() {
        testMessageSource = new TestMessageSource(messageSource);

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
    void createTrade_tradeNotExist() throws MyException {
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
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.createTrade(tradeSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.trade.tradeExists", new Object[] { tradeSave.getTradeId() });
    }

    // -----------------------------------------------------------------------------------------------
    // getTradeById method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    void getTradeById_tradeExist() throws MyException {
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
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.getTradeById(tradeId));
        // THEN
        testMessageSource.compare(exception
                            , "exception.trade.unknown", new Object[] { tradeId });
    }

    // -----------------------------------------------------------------------------------------------
    // updateTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    void updateTrade_tradeExist() throws MyException {
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
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.updateTrade(tradeId, tradeSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.trade.unknown", new Object[] { tradeId });
    }

    // -----------------------------------------------------------------------------------------------
    // deleteTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    void deleteTrade_tradeExist() throws MyException {
        // GIVEN
        // WHEN
        tradeBusiness.deleteTrade(tradeId);
        // THEN
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.getTradeById(tradeId));
        testMessageSource.compare(exception
                            , "exception.trade.unknown", new Object[] { tradeId });
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void deleteTrade_tradeNotExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.deleteTrade(tradeId));
        // THEN
        testMessageSource.compare(exception
                            , "exception.trade.unknown", new Object[] { tradeId });
    }
}

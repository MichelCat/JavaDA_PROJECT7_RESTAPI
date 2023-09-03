package com.nnk.poseidon.business;

import com.nnk.poseidon.data.TradeData;
import com.nnk.poseidon.model.Trade;
import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * TradeBusinessTest is a unit testing class of the Trades service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class TradeBusinessTest {

    @Autowired
    private TradeBusiness tradeBusiness;
    @Autowired
    private MessageSource messageSource;

    @MockBean
    private TradeRepository tradeRepository;

    private TestMessageSource testMessageSource;
    private Trade tradeSource;
    private Trade tradeSave;
    public List<Trade> tradeList;

    @BeforeEach
    public void setUpBefore() {
        testMessageSource = new TestMessageSource(messageSource);

        tradeSource = TradeData.getTradeSource();
        tradeSave = TradeData.getTradeSave();

        tradeList = new ArrayList<>();
        tradeList.add(tradeSave);
    }

    // -----------------------------------------------------------------------------------------------
    // getTradesList method
    // -----------------------------------------------------------------------------------------------
    @Test
    void getTradesList_findAllNormal() {
        // GIVEN
        when(tradeRepository.findAll()).thenReturn(tradeList);
        // WHEN
        assertThat(tradeBusiness.getTradesList()).isEqualTo(tradeList);
        // THEN
        verify(tradeRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getTradesList_findAllEmpty() {
        // GIVEN
        when(tradeRepository.findAll()).thenReturn(new ArrayList<>());
        // WHEN
        assertThat(tradeBusiness.getTradesList()).isEmpty();
        // THEN
        verify(tradeRepository, Mockito.times(1)).findAll();
    }

    // -----------------------------------------------------------------------------------------------
    // createTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    void createTrade_saveNormal() throws MyException {
        // GIVEN
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(tradeRepository.save(tradeSource)).thenReturn(tradeSave);
        // WHEN
        assertThat(tradeBusiness.createTrade(tradeSource)).isEqualTo(tradeSave);
        // THEN
        verify(tradeRepository, Mockito.times(1)).save(any(Trade.class));
    }

    @Test
    void createTrade_nullTradeParameter_returnNullPointer() {
        // GIVEN
        when(tradeRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.createTrade(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.trade.nullTrade", null);
        verify(tradeRepository, Mockito.times(0)).save(any(Trade.class));
    }

    @Test
    void createTrade_TradeExist_returnMyException() {
        // GIVEN
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.of(tradeSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.createTrade(tradeSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.trade.tradeExists", new Object[] { tradeSave.getTradeId() });
        verify(tradeRepository, Mockito.times(0)).save(any(Trade.class));
    }

    // -----------------------------------------------------------------------------------------------
    // getTradeById method
    // -----------------------------------------------------------------------------------------------
    @Test
    void getTradeById_findByIdNormal() throws MyException {
        // GIVEN
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.of(tradeSave));
        // WHEN
        assertThat(tradeBusiness.getTradeById(1)).isEqualTo(tradeSave);
        // THEN
        verify(tradeRepository, Mockito.times(1)).findById(any(Integer.class));
    }

    @Test
    void getTradeById_nullIdParameter_returnMyException() {
        // GIVEN
        when(tradeRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.getTradeById(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.trade.unknown", new Object[] { null });
        verify(tradeRepository, Mockito.times(1)).findById(null);
    }

    // -----------------------------------------------------------------------------------------------
    // updateTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    void updateTrade_updateNormal() throws MyException {
        // GIVEN
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.of(tradeSave));
        when(tradeRepository.save(tradeSave)).thenReturn(tradeSave);
        // WHEN
        assertThat(tradeBusiness.updateTrade(1, tradeSource)).isEqualTo(tradeSave);
        // THEN
        verify(tradeRepository, Mockito.times(1)).save(any(Trade.class));
    }

    @Test
    void updateTrade_TradeNotExist_returnMyException() {
        // GIVEN
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.updateTrade(2, tradeSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.trade.unknown", new Object[] { 2 });
        verify(tradeRepository, Mockito.times(0)).save(any(Trade.class));
    }

    @Test
    void updateTrade_nullIdParameter_returnMyException() {
        // GIVEN
        when(tradeRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.updateTrade(null, tradeSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.trade.unknown", new Object[] { null });
        verify(tradeRepository, Mockito.times(0)).save(any(Trade.class));
    }

    @Test
    void updateTrade_zeroIdParameter_returnMyException() {
        // GIVEN
        when(tradeRepository.findById(0)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.updateTrade(0, tradeSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.trade.unknown", new Object[] { 0 });
        verify(tradeRepository, Mockito.times(0)).save(any(Trade.class));
    }

    @Test
    void updateTrade_nullTradeParameter_returnMyException() {
        // GIVEN
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.of(tradeSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.updateTrade(1, null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.trade.nullTrade", null);
        verify(tradeRepository, Mockito.times(0)).save(any(Trade.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    void deleteTrade_deleteNormal() throws MyException {
        // GIVEN
        when(tradeRepository.existsById(any(Integer.class))).thenReturn(true);
        doNothing().when(tradeRepository).deleteById(any(Integer.class));
        // WHEN
        tradeBusiness.deleteTrade(1);
        // THEN
        verify(tradeRepository, Mockito.times(1)).deleteById(any(Integer.class));
    }

    @Test
    void deleteTrade_TradeNotExist_returnMyException() {
        // GIVEN
        when(tradeRepository.existsById(any(Integer.class))).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.deleteTrade(2));
        // THEN
        testMessageSource.compare(exception
                            , "exception.trade.unknown", new Object[] { 2 });
        verify(tradeRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    void deleteTrade_nullIdParameter_returnMyException() {
        // GIVEN
        when(tradeRepository.existsById(null)).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.deleteTrade(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.trade.unknown", new Object[] { null });
        verify(tradeRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    void deleteTrade_zeroIdParameter_returnMyException() {
        // GIVEN
        when(tradeRepository.existsById(0)).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> tradeBusiness.deleteTrade(0));
        // THEN
        testMessageSource.compare(exception
                            , "exception.trade.unknown", new Object[] { 0 });
        verify(tradeRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }
}

package com.nnk.poseidon.business;

import com.nnk.poseidon.data.TradeData;
import com.nnk.poseidon.model.Trade;
import com.nnk.poseidon.exception.MyExceptionBadRequestException;
import com.nnk.poseidon.exception.MyExceptionNotFoundException;
import com.nnk.poseidon.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class TradeBusinessTest {

    @Autowired
    private TradeBusiness tradeBusiness;

    @MockBean
    private TradeRepository tradeRepository;

    private Trade tradeSource;
    private Trade tradeSave;
    public List<Trade> tradeList;

    @BeforeEach
    public void setUpBefore() {
        tradeSource = TradeData.getTradeSource();
        tradeSave = TradeData.getTradeSave();

        tradeList = new ArrayList<>();
        tradeList.add(tradeSave);
    }

    // -----------------------------------------------------------------------------------------------
    // getTradesList method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void getTradesList_findAllNormal() {
        // GIVEN
        when(tradeRepository.findAll()).thenReturn(tradeList);
        // WHEN
        assertThat(tradeBusiness.getTradesList()).isEqualTo(tradeList);
        // THEN
        verify(tradeRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getTradesList_findAllEmpty() {
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
    public void createTrade_saveNormal() {
        // GIVEN
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(tradeRepository.save(tradeSource)).thenReturn(tradeSave);
        // WHEN
        assertThat(tradeBusiness.createTrade(tradeSource)).isEqualTo(tradeSave);
        // THEN
        verify(tradeRepository, Mockito.times(1)).save(any(Trade.class));
    }

    @Test
    public void createTrade_nullTradeParameter_returnNullPointer() {
        // GIVEN
        when(tradeRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> tradeBusiness.createTrade(null));
        // THEN
        verify(tradeRepository, Mockito.times(0)).save(any(Trade.class));
    }

    @Test
    public void createTrade_TradeExist_returnBadRequest() {
        // GIVEN
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.of(tradeSave));
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> tradeBusiness.createTrade(tradeSave));
        // THEN
        verify(tradeRepository, Mockito.times(0)).save(any(Trade.class));
    }

    // -----------------------------------------------------------------------------------------------
    // getTradeById method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void getTradeById_findByIdNormal() {
        // GIVEN
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.of(tradeSave));
        // WHEN
        assertThat(tradeBusiness.getTradeById(1)).isEqualTo(tradeSave);
        // THEN
        verify(tradeRepository, Mockito.times(1)).findById(any(Integer.class));
    }

    @Test
    public void getTradeById_nullIdParameter_returnNotFound() {
        // GIVEN
        when(tradeRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.getTradeById(null));
        // THEN
        verify(tradeRepository, Mockito.times(1)).findById(null);
    }

    // -----------------------------------------------------------------------------------------------
    // updateTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void updateTrade_updateNormal() {
        // GIVEN
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.of(tradeSave));
        when(tradeRepository.save(tradeSave)).thenReturn(tradeSave);
        // WHEN
        assertThat(tradeBusiness.updateTrade(1, tradeSource)).isEqualTo(tradeSave);
        // THEN
        verify(tradeRepository, Mockito.times(1)).save(any(Trade.class));
    }

    @Test
    public void updateTrade_TradeNotExist_returnNotFound() {
        // GIVEN
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.updateTrade(2, tradeSource));
        // THEN
        verify(tradeRepository, Mockito.times(0)).save(any(Trade.class));
    }

    @Test
    public void updateTrade_nullIdParameter_returnNotFound() {
        // GIVEN
        when(tradeRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.updateTrade(null, tradeSource));
        // THEN
        verify(tradeRepository, Mockito.times(0)).save(any(Trade.class));
    }

    @Test
    public void updateTrade_zeroIdParameter_returnNotFound() {
        // GIVEN
        when(tradeRepository.findById(0)).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.updateTrade(0, tradeSource));
        // THEN
        verify(tradeRepository, Mockito.times(0)).save(any(Trade.class));
    }

    @Test
    public void updateTrade_nullTradeParameter_returnNotFound() {
        // GIVEN
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.of(tradeSave));
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> tradeBusiness.updateTrade(1, null));
        // THEN
        verify(tradeRepository, Mockito.times(0)).save(any(Trade.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void deleteTrade_deleteNormal() {
        // GIVEN
        when(tradeRepository.existsById(any(Integer.class))).thenReturn(true);
        doNothing().when(tradeRepository).deleteById(any(Integer.class));
        // WHEN
        tradeBusiness.deleteTrade(1);
        // THEN
        verify(tradeRepository, Mockito.times(1)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteTrade_TradeNotExist_returnNotFound() {
        // GIVEN
        when(tradeRepository.existsById(any(Integer.class))).thenReturn(false);
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.deleteTrade(2));
        // THEN
        verify(tradeRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteTrade_nullIdParameter_returnNotFound() {
        // GIVEN
        when(tradeRepository.existsById(null)).thenReturn(false);
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.deleteTrade(null));
        // THEN
        verify(tradeRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteTrade_zeroIdParameter_returnNotFound() {
        // GIVEN
        when(tradeRepository.existsById(0)).thenReturn(false);
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> tradeBusiness.deleteTrade(0));
        // THEN
        verify(tradeRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }
}

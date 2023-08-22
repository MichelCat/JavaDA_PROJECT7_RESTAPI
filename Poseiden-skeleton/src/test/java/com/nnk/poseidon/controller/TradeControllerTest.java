package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.TradeBusiness;
import com.nnk.poseidon.data.TradeData;
import com.nnk.poseidon.model.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * TradeControllerTest is a class of Endpoint unit tests on Trade.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@WebMvcTest(controllers = TradeController.class)
@ActiveProfiles("test")
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TradeBusiness tradeBusiness;

    public List<Trade> tradeList;

    private Trade tradeSource;
    private Trade tradeSave;
    private MultiValueMap<String, String> tradeSourceController;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        tradeSource = TradeData.getTradeSource();
        tradeSave = TradeData.getTradeSave();
        tradeSourceController = TradeData.getTradeSourceController();

        tradeList = new ArrayList<>();
        tradeList.add(tradeSave);
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    void home_getTrades_return200() throws Exception {
        // GIVEN
        when(tradeBusiness.getTradesList()).thenReturn(tradeList);
        // WHEN
        mockMvc.perform(get("/trade/list")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("trades", tradeList))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // addTradeForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    void addTradeForm_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/trade/add")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().errorCount(0))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // validate method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    void validate_tradeNotExist_return302() throws Exception {
        // GIVEN
        when(tradeBusiness.createTrade(tradeSource)).thenReturn(tradeSave);
        when(tradeBusiness.getTradesList()).thenReturn(tradeList);
        // WHEN
        mockMvc.perform(post("/trade/validate")
                        .with(csrf().asHeader())
                        .params(tradeSourceController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/trade/list"))
                .andDo(print());
        // THEN
        verify(tradeBusiness, Mockito.times(1)).createTrade(any(Trade.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    void validate_tradeNotValid() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/trade/validate")
                        .with(csrf().asHeader())
                        .param("Account", "")
                        .param("Type", "")
                        .param("buyQuantity", "")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(model().errorCount(3))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeHasFieldErrorCode("trade","account","NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("trade","type","NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("trade","buyQuantity","NotNull"))
                .andDo(print());
        // THEN
        verify(tradeBusiness, Mockito.times(0)).createTrade(any(Trade.class));
    }

    // -----------------------------------------------------------------------------------------------
    // showUpdateForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    void showUpdateForm_tradeExist_return200() throws Exception {
        // GIVEN
        when(tradeBusiness.getTradeById(1)).thenReturn(tradeSave);
        // WHEN
        mockMvc.perform(get("/trade/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("trade", tradeSave))
                .andExpect(view().name("trade/update"))
                .andDo(print());
        // THEN
        verify(tradeBusiness, Mockito.times(1)).getTradeById(any(Integer.class));
    }

    // -----------------------------------------------------------------------------------------------
    // updateTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    void updateTrade_tradeExist_return302() throws Exception {
        // GIVEN
        when(tradeBusiness.updateTrade(1, tradeSave)).thenReturn(tradeSave);
        when(tradeBusiness.getTradesList()).thenReturn(tradeList);
        // WHEN
        mockMvc.perform(patch("/trade/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(tradeSourceController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/trade/list"))
                .andDo(print());
        // THEN
        verify(tradeBusiness, Mockito.times(1)).updateTrade(any(Integer.class), any(Trade.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateTrade_tradeNotValid() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/trade/update/{id}", 1)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(3))
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeHasFieldErrorCode("trade","account","NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("trade","type","NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("trade","buyQuantity","NotNull"))
                .andDo(print());
        // THEN
        verify(tradeBusiness, Mockito.times(0)).updateTrade(any(Integer.class), any(Trade.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    void deleteTrade_tradeExist_return302() throws Exception {
        // GIVEN
        when(tradeBusiness.getTradesList()).thenReturn(tradeList);
        doNothing().when(tradeBusiness).deleteTrade(any(Integer.class));
        // WHEN
        mockMvc.perform(get("/trade/delete/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/trade/list"))
                .andDo(print());
        // THEN
        verify(tradeBusiness, Mockito.times(1)).deleteTrade(any(Integer.class));
    }
}

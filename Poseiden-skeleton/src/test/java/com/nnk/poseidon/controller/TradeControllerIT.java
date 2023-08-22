package com.nnk.poseidon.controller;

import com.nnk.poseidon.Application;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.TradeData;
import com.nnk.poseidon.model.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * TradeControllerIT is a class of Endpoint integration tests on Trade.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
class TradeControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    public List<Trade> tradeList;

    private Trade tradeSave;
    private MultiValueMap<String, String> tradeSourceController;
    private MultiValueMap<String, String> tradeSaveController;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        tradeSave = TradeData.getTradeSave();
        tradeSourceController = TradeData.getTradeSourceController();
        tradeSaveController = TradeData.getTradeSaveController();

        tradeList = new ArrayList<>();
        tradeList.add(tradeSave);
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    void home_getTrades_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/trade/list")
                        .with(csrf().asHeader())
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
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void addTradeForm_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/trade/add")
                        .with(csrf().asHeader())
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
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void validate_tradeNotExist_return302() throws Exception {
        // GIVEN
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
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    void validate_tradeExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/trade/validate")
                        .with(csrf().asHeader())
                        .params(tradeSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // showUpdateForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    void showUpdateForm_tradeExist_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/trade/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(tradeSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("trade", tradeSave))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // updateTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    void updateTrade_tradeExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/trade/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(tradeSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/trade/list"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void updateTrade_tradeNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/trade/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(tradeSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // deleteTrade method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = TradeData.scriptCreateTrade)
    void deleteTrade_tradeExist_return302() throws Exception {
        // GIVEN
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
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void deleteTrade_tradeNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/trade/delete/{id}", 1)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
    }
}

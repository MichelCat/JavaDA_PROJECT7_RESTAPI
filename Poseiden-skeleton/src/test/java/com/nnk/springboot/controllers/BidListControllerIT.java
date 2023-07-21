package com.nnk.springboot.controllers;

import com.nnk.springboot.Application;
import com.nnk.springboot.Exception.MyExceptionBadRequestException;
import com.nnk.springboot.Exception.MyExceptionNotFoundException;
import com.nnk.springboot.data.BidData;
import com.nnk.springboot.domain.BidList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * BidListControllerIT is a class of Endpoint integration tests on bid.
 *
 * @author MC
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class BidListControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    public List<BidList> bidsList;

    @Before
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();

        bidsList = new ArrayList<>();
        bidsList.add(BidData.getBidSave());
    }

    // -----------------------------------------------------------------------------------------------
    // Home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql(scripts = BidData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    public void home_getBids_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/bidList/list")
                    .with(csrf().asHeader())
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("bidList", bidsList))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // AddBidForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql(scripts = BidData.scriptClearDataBase)
    public void addBidForm_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/bidList/add")
                    .with(csrf().asHeader())
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().errorCount(0))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // Validate method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql(scripts = BidData.scriptClearDataBase)
    public void validate_bidNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/bidList/validate")
                    .with(csrf().asHeader())
                    .params(BidData.getBidSourceController())
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/bidList/list"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql(scripts = BidData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    public void validate_bidExist_return400() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/bidList/validate")
                    .with(csrf().asHeader())
                    .params(BidData.getBidSaveController())
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MyExceptionBadRequestException))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // ShowUpdateForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql(scripts = BidData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    public void showUpdateForm_bidExist_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/bidList/update/{id}", 1)
                    .with(csrf().asHeader())
                    .params(BidData.getBidSaveController())
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("bidList", BidData.getBidSave()))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // UpdateBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql(scripts = BidData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    public void updateBid_bidExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/bidList/update/{id}", 1)
                    .with(csrf().asHeader())
                    .params(BidData.getBidSaveController())
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/bidList/list"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql(scripts = BidData.scriptClearDataBase)
    public void updateBid_bidNotExist_return404() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/bidList/update/{id}", 1)
                    .with(csrf().asHeader())
                    .params(BidData.getBidSaveController())
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MyExceptionNotFoundException))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // DeleteBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql(scripts = BidData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    public void deleteBid_bidExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/bidList/delete/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/bidList/list"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql(scripts = BidData.scriptClearDataBase)
    public void deleteBid_bidNotExist_return404() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/bidList/delete/{id}", 1)
                    .with(csrf().asHeader())
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MyExceptionNotFoundException))
                .andDo(print());
        // THEN
    }
}

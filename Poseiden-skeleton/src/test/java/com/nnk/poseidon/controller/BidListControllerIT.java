package com.nnk.poseidon.controller;

import com.nnk.poseidon.Application;
import com.nnk.poseidon.Retention.TestWithMockUser;
import com.nnk.poseidon.Retention.WithMockUserRoleUser;
import com.nnk.poseidon.data.BidData;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.model.Bid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * BidListControllerIT is a class of Endpoint integration tests on bid.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
class BidListControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private TestWithMockUser testWithMockUser;

    public List<Bid> bidsList;

    private Bid bidSave;
    private MultiValueMap<String, String> bidSourceController;
    private MultiValueMap<String, String> bidSaveController;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();

        testWithMockUser = new TestWithMockUser();

        bidSave = BidData.getBidSave();
        bidSourceController = BidData.getBidSourceController();
        bidSaveController = BidData.getBidSaveController();

        bidsList = new ArrayList<>();
        bidsList.add(bidSave);
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    void home_getBids_return200() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/bidList/list")
                    .with(csrf().asHeader())
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("bids", bidsList))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    // -----------------------------------------------------------------------------------------------
    // addBidForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void addBidForm_return200() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/bidList/add")
                    .with(csrf().asHeader())
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().errorCount(0))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    // -----------------------------------------------------------------------------------------------
    // validate method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void validate_bidNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(post("/bidList/validate")
                    .with(csrf().asHeader())
                    .params(bidSourceController)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/bidList/list"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    void validate_bidExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(post("/bidList/validate")
                    .with(csrf().asHeader())
                    .params(bidSaveController)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    // -----------------------------------------------------------------------------------------------
    // showUpdateForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    void showUpdateForm_bidExist_return200() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/bidList/update/{id}", 1)
                    .with(csrf().asHeader())
                    .params(bidSaveController)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("bid", bidSave))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    // -----------------------------------------------------------------------------------------------
    // updateBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    void updateBid_bidExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(patch("/bidList/update/{id}", 1)
                    .with(csrf().asHeader())
                    .params(bidSaveController)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/bidList/list"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void updateBid_bidNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(patch("/bidList/update/{id}", 1)
                    .with(csrf().asHeader())
                    .params(bidSaveController)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    // -----------------------------------------------------------------------------------------------
    // deleteBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = BidData.scriptCreateBid)
    void deleteBid_bidExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/bidList/delete/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/bidList/list"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void deleteBid_bidNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/bidList/delete/{id}", 1)
                    .with(csrf().asHeader())
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }
}

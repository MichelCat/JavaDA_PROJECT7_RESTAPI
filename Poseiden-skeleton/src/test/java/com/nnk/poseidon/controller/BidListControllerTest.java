package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.BidListBusiness;
import com.nnk.poseidon.data.BidData;
import com.nnk.poseidon.model.Bid;
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
 * BidListControllerTest is a class of Endpoint unit tests on bid.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@WebMvcTest(controllers = BidListController.class)
@ActiveProfiles("test")
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private BidListBusiness bidListBusiness;

    public List<Bid> bidsList;

    private Bid bidSource;
    private Bid bidSave;
    private MultiValueMap<String, String> bidSourceController;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        bidSource = BidData.getBidSource();
        bidSave = BidData.getBidSave();
        bidSourceController = BidData.getBidSourceController();

        bidsList = new ArrayList<>();
        bidsList.add(bidSave);
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void home_getBids_return200() throws Exception {
        // GIVEN
        when(bidListBusiness.getBidsList()).thenReturn(bidsList);
        // WHEN
        mockMvc.perform(get("/bidList/list")
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("bids", bidsList))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // addBidForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void addBidForm_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/bidList/add")
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().errorCount(0))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // validate method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void validate_bidNotExist_return302() throws Exception {
        // GIVEN
        when(bidListBusiness.createBid(bidSource)).thenReturn(bidSave);
        when(bidListBusiness.getBidsList()).thenReturn(bidsList);
        // WHEN
        mockMvc.perform(post("/bidList/validate")
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
        verify(bidListBusiness, Mockito.times(1)).createBid(any(Bid.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void validate_bidNotValid() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/bidList/validate")
                    .with(csrf().asHeader())
                    .param("account", "")
                    .param("type", "")
                    .param("bidQuantity", "")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(model().errorCount(3))
            .andExpect(status().isOk())
            .andExpect(view().name("bidList/add"))
            .andExpect(model().attributeHasFieldErrorCode("bid","account","NotBlank"))
            .andExpect(model().attributeHasFieldErrorCode("bid","account","NotBlank"))
            .andExpect(model().attributeHasFieldErrorCode("bid","bidQuantity","NotNull"))
            .andDo(print());
        // THEN
        verify(bidListBusiness, Mockito.times(0)).createBid(any(Bid.class));
    }

    // -----------------------------------------------------------------------------------------------
    // showUpdateForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void showUpdateForm_bidExist_return200() throws Exception {
        // GIVEN
        when(bidListBusiness.getBidById(1)).thenReturn(bidSave);
        // WHEN
        mockMvc.perform(get("/bidList/update/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("bid", bidSave))
                .andExpect(view().name("bidList/update"))
                .andDo(print());
        // THEN
        verify(bidListBusiness, Mockito.times(1)).getBidById(any(Integer.class));
    }

    // -----------------------------------------------------------------------------------------------
    // updateBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void updateBid_bidExist_return302() throws Exception {
        // GIVEN
        when(bidListBusiness.updateBid(1, bidSave)).thenReturn(bidSave);
        when(bidListBusiness.getBidsList()).thenReturn(bidsList);
        // WHEN
        mockMvc.perform(patch("/bidList/update/{id}", 1)
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
        verify(bidListBusiness, Mockito.times(1)).updateBid(any(Integer.class), any(Bid.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateBid_bidNotValid() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/bidList/update/{id}", 1)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(3))
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeHasFieldErrorCode("bid","account","NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("bid","account","NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("bid","bidQuantity","NotNull"))
                .andDo(print());
        // THEN
        verify(bidListBusiness, Mockito.times(0)).updateBid(any(Integer.class), any(Bid.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void deleteBid_bidExist_return302() throws Exception {
        // GIVEN
        when(bidListBusiness.getBidsList()).thenReturn(bidsList);
        doNothing().when(bidListBusiness).deleteBid(any(Integer.class));
        // WHEN
        mockMvc.perform(get("/bidList/delete/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/bidList/list"))
                .andDo(print());
        // THEN
        verify(bidListBusiness, Mockito.times(1)).deleteBid(any(Integer.class));
    }
}

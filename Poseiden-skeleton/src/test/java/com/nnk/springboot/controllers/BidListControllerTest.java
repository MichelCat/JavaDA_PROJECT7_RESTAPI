package com.nnk.springboot.controllers;

import com.nnk.springboot.business.BidListBusiness;
import com.nnk.springboot.data.BidData;
import com.nnk.springboot.domain.BidList;
import jakarta.servlet.ServletException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * BidListControllerTest is a class of Endpoint unit tests on bid.
 *
 * @author MC
 * @version 1.0
 */
@RunWith(SpringRunner.class)
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
                .andExpect(model().attribute("bidList", bidsList))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // AddBidForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
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
    // Validate method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
    public void validate_bidNotExist_return302() throws Exception {
        // GIVEN
        when(bidListBusiness.createBid(BidData.getBidSource())).thenReturn(BidData.getBidSave());
        when(bidListBusiness.getBidsList()).thenReturn(bidsList);
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
        verify(bidListBusiness, Mockito.times(1)).createBid(any(BidList.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void validate_bidNotValid() throws Exception {
        // GIVEN
        // WHEN
        ServletException exception = assertThrows(ServletException.class,
            () -> {mockMvc.perform(post("/bidList/validate")
                    .with(csrf().asHeader())
                    .contentType(MediaType.APPLICATION_JSON)
            );
        });
        // THEN
        assertThat(exception.getMessage()).contains("validate.bid.bidQuantity: Bid quantity cannot be null");
        assertThat(exception.getMessage()).contains("validate.bid.account: Account is mandatory");
        assertThat(exception.getMessage()).contains("validate.bid.type: Type is mandatory");
    }

    // -----------------------------------------------------------------------------------------------
    // ShowUpdateForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
    public void showUpdateForm_bidExist_return200() throws Exception {
        // GIVEN
        when(bidListBusiness.getBidById(1)).thenReturn(BidData.getBidSave());
        // WHEN
        mockMvc.perform(get("/bidList/update/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("bidList", BidData.getBidSave()))
                .andExpect(view().name("bidList/update"))
                .andDo(print());
        // THEN
        verify(bidListBusiness, Mockito.times(1)).getBidById(any(Integer.class));
    }

    // -----------------------------------------------------------------------------------------------
    // UpdateBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateBid_bidExist_return302() throws Exception {
        // GIVEN
        when(bidListBusiness.updateBid(1, BidData.getBidSave())).thenReturn(BidData.getBidSave());
        when(bidListBusiness.getBidsList()).thenReturn(bidsList);
        // WHEN
        mockMvc.perform(post("/bidList/update/{id}", 1)
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
        verify(bidListBusiness, Mockito.times(1)).updateBid(any(Integer.class), any(BidList.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateBid_bidNotValid() throws Exception {
        // GIVEN
        // WHEN
        ServletException exception = assertThrows(ServletException.class,
                () -> {mockMvc.perform(post("/bidList/update/{id}", 0)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                );
                });
        // THEN
        assertThat(exception.getMessage()).contains("updateBid.id: must be greater than 0");
        assertThat(exception.getMessage()).contains("updateBid.bidList.account: Account is mandatory");
        assertThat(exception.getMessage()).contains("updateBid.bidList.type: Type is mandatory");
        assertThat(exception.getMessage()).contains("updateBid.bidList.bidQuantity: Bid quantity cannot be null");
    }

    // -----------------------------------------------------------------------------------------------
    // DeleteBid method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
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
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/bidList/list"))
                .andDo(print());
        // THEN
        verify(bidListBusiness, Mockito.times(1)).deleteBid(any(Integer.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteBid_bidNotValid() throws Exception {
        // GIVEN
        // WHEN
        ServletException exception = assertThrows(ServletException.class,
                () -> {mockMvc.perform(get("/bidList/delete/{id}", 0)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                );
                });
        // THEN
        assertThat(exception.getMessage()).contains("deleteBid.id: must be greater than 0");
    }
}

package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.RatingBusiness;
import com.nnk.poseidon.data.RatingData;
import com.nnk.poseidon.model.Rating;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * RatingControllerTest is a class of Endpoint unit tests on Rating.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@WebMvcTest(controllers = RatingController.class)
@ActiveProfiles("test")
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private RatingBusiness ratingBusiness;

    public List<Rating> ratingList;

    private Rating ratingSource;
    private Rating ratingSave;
    private MultiValueMap<String, String> ratingSourceController;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        ratingSource = RatingData.getRatingSource();
        ratingSave = RatingData.getRatingSave();
        ratingSourceController = RatingData.getRatingSourceController();

        ratingList = new ArrayList<>();
        ratingList.add(ratingSave);
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void home_getRatings_return200() throws Exception {
        // GIVEN
        when(ratingBusiness.getRatingsList()).thenReturn(ratingList);
        // WHEN
        mockMvc.perform(get("/rating/list")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("ratings", ratingList))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // addRatingForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void addRatingForm_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/rating/add")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().errorCount(0))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // validate method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void validate_ratingNotExist_return302() throws Exception {
        // GIVEN
        when(ratingBusiness.createRating(ratingSource)).thenReturn(ratingSave);
        when(ratingBusiness.getRatingsList()).thenReturn(ratingList);
        // WHEN
        mockMvc.perform(post("/rating/validate")
                        .with(csrf().asHeader())
                        .params(ratingSourceController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/rating/list"))
                .andDo(print());
        // THEN
        verify(ratingBusiness, Mockito.times(1)).createRating(any(Rating.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void validate_ratingNotValid() throws Exception {
        // GIVEN
        // WHEN
        MvcResult mvcResult = mockMvc.perform(post("/rating/validate")
                        .with(csrf().asHeader())
                        .param("moodysRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(model().errorCount(4))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andDo(print())
                .andReturn();
        // THEN
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Moodys rating is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Sand PRating is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Fitch rating is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Order number must not be null");
        verify(ratingBusiness, Mockito.times(0)).createRating(any(Rating.class));
    }

    // -----------------------------------------------------------------------------------------------
    // showUpdateForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void showUpdateForm_ratingExist_return200() throws Exception {
        // GIVEN
        when(ratingBusiness.getRatingById(1)).thenReturn(ratingSave);
        // WHEN
        mockMvc.perform(get("/rating/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("rating", ratingSave))
                .andExpect(view().name("rating/update"))
                .andDo(print());
        // THEN
        verify(ratingBusiness, Mockito.times(1)).getRatingById(any(Integer.class));
    }

    // -----------------------------------------------------------------------------------------------
    // updateRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void updateRating_ratingExist_return302() throws Exception {
        // GIVEN
        when(ratingBusiness.updateRating(1, ratingSave)).thenReturn(ratingSave);
        when(ratingBusiness.getRatingsList()).thenReturn(ratingList);
        // WHEN
        mockMvc.perform(patch("/rating/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(ratingSourceController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/rating/list"))
                .andDo(print());
        // THEN
        verify(ratingBusiness, Mockito.times(1)).updateRating(any(Integer.class), any(Rating.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateRating_ratingNotValid() throws Exception {
        // GIVEN
        // WHEN
        MvcResult mvcResult = mockMvc.perform(patch("/rating/update/{id}", 1)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(4))
                .andExpect(view().name("rating/update"))
                .andDo(print())
                .andReturn();
        // THEN
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Moodys rating is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Sand PRating is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Fitch rating is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Order number must not be null");
        verify(ratingBusiness, Mockito.times(0)).updateRating(any(Integer.class), any(Rating.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateRating_idZero() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/rating/update/{id}", 0)
                        .with(csrf().asHeader())
                        .params(ratingSourceController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("rating/update"))
                .andDo(print());
        // THEN
        verify(ratingBusiness, Mockito.times(0)).updateRating(any(Integer.class), any(Rating.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void deleteRating_ratingExist_return302() throws Exception {
        // GIVEN
        when(ratingBusiness.getRatingsList()).thenReturn(ratingList);
        doNothing().when(ratingBusiness).deleteRating(any(Integer.class));
        // WHEN
        mockMvc.perform(get("/rating/delete/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/rating/list"))
                .andDo(print());
        // THEN
        verify(ratingBusiness, Mockito.times(1)).deleteRating(any(Integer.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void deleteRating_idZero() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/rating/delete/{id}", 0)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/rating/list"))
                .andDo(print());
        // THEN
        verify(ratingBusiness, Mockito.times(0)).deleteRating(any(Integer.class));
    }
}

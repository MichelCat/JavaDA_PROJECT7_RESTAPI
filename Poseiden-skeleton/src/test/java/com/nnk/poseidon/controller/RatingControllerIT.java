package com.nnk.poseidon.controller;

import com.nnk.poseidon.Application;
import com.nnk.poseidon.Retention.TestWithMockUser;
import com.nnk.poseidon.Retention.WithMockUserRoleUser;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.RatingData;
import com.nnk.poseidon.model.Rating;
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
 * RatingControllerIT is a class of Endpoint integration tests on Rating.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
class RatingControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private TestWithMockUser testWithMockUser;

    public List<Rating> ratingList;

    private Rating ratingSave;
    private MultiValueMap<String, String> ratingSourceController;
    private MultiValueMap<String, String> ratingSaveController;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        testWithMockUser = new TestWithMockUser();

        ratingSave = RatingData.getRatingSave();
        ratingSourceController = RatingData.getRatingSourceController();
        ratingSaveController = RatingData.getRatingSaveController();

        ratingList = new ArrayList<>();
        ratingList.add(ratingSave);
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    void home_getRatings_return200() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/rating/list")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("ratings", ratingList))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    // -----------------------------------------------------------------------------------------------
    // addRatingForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void addRatingForm_return200() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/rating/add")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
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
    void validate_ratingNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(post("/rating/validate")
                        .with(csrf().asHeader())
                        .params(ratingSourceController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/rating/list"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    void validate_ratingExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(post("/rating/validate")
                        .with(csrf().asHeader())
                        .params(ratingSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/rating/list"))
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
    @Sql(scripts = RatingData.scriptCreateRating)
    void showUpdateForm_ratingExist_return200() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/rating/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(ratingSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("rating", ratingSave))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    // -----------------------------------------------------------------------------------------------
    // updateRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    void updateRating_ratingExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(patch("/rating/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(ratingSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/rating/list"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void updateRating_ratingNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(patch("/rating/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(ratingSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    void deleteRating_ratingExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/rating/delete/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/rating/list"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void deleteRating_ratingNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/rating/delete/{id}", 1)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }
}

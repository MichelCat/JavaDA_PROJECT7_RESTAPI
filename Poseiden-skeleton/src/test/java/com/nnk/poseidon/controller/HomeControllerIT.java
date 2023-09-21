package com.nnk.poseidon.controller;

import com.nnk.poseidon.Application;
import com.nnk.poseidon.Retention.TestWithMockUser;
import com.nnk.poseidon.Retention.WithMockAdminRoleAdmin;
import com.nnk.poseidon.Retention.WithMockUserRoleUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * HomeControllerIT is a class of Endpoint integration tests on admin login page.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
class HomeControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private TestWithMockUser testWithMockUser;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        testWithMockUser = new TestWithMockUser();
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockAdminRoleAdmin
    void home_return200() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/home/admin")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().errorCount(0))
                .andDo(print());
        // THEN
        testWithMockUser.checkAdminRoleAdmin(result);
    }

    // -----------------------------------------------------------------------------------------------
    // adminHome method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    void adminHome_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/home/user")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().errorCount(0))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }
}

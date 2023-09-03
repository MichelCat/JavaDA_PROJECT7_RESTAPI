package com.nnk.poseidon.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * HomeControllerTest is a class of Endpoint unit tests on admin login page.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@WebMvcTest(controllers = HomeController.class)
@ActiveProfiles("test")
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
    void home_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/admin/home")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().errorCount(0))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // adminHome method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    void adminHome_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/user/home")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().errorCount(0))
                .andDo(print());
        // THEN
    }
}

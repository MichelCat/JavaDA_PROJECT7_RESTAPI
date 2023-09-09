package com.nnk.poseidon.controller;

import com.nnk.poseidon.Application;
import com.nnk.poseidon.enumerator.ApplicationLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * LanguageControllerIT is a class of Endpoint integration tests on language change .
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
class LanguageControllerIT {

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
    // changeLanguage method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    void changeLanguage_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/change-language")
                        .with(csrf().asHeader())
                        .param("lang", ApplicationLanguage.en.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "USER")
    void changeLanguage_langBlank_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/change-language")
                        .with(csrf().asHeader())
                        .param("lang", "")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
    }
}

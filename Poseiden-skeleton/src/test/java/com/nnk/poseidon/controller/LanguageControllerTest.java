package com.nnk.poseidon.controller;

import com.nnk.poseidon.Retention.WithMockRoleUser;
import com.nnk.poseidon.business.LanguageBusiness;
import com.nnk.poseidon.enumerator.ApplicationLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * LanguageControllerTest is a class of Endpoint unit tests on language change .
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@WebMvcTest(controllers = LanguageController.class)
@ActiveProfiles("test")
class LanguageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private LanguageBusiness languageBusiness;

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
    @WithMockRoleUser
    void changeLanguage_return302() throws Exception {
        // GIVEN
        doNothing().when(languageBusiness).changeLanguage(Optional.of(ApplicationLanguage.en.toString()));
        // WHEN
        mockMvc.perform(get("/change-language")
                        .param("lang",ApplicationLanguage.en.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andDo(print());
        // THEN
        verify(languageBusiness, Mockito.times(1)).changeLanguage(any(Optional.class));
    }
}

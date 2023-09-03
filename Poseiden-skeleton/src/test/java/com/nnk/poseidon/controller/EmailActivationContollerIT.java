package com.nnk.poseidon.controller;

import com.nnk.poseidon.Application;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.UserData;
import com.nnk.poseidon.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * EmailActivationBusinessIT is a class of Endpoint integration tests on email activation.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
class EmailActivationContollerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private User userSave;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        userSave = UserData.getUserSave();
   }

    // -----------------------------------------------------------------------------------------------
    // accountActivation method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void accountActivation_userNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/app/register/{key}", userSave.getEmailValidationKey())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/app/login"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
    }
}

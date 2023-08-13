package com.nnk.poseidon.controller;

import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.business.EmailActivationBusiness;
import com.nnk.poseidon.data.UserData;
import com.nnk.poseidon.model.User;
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
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * EmailActivationBusinessTest is a class of Endpoint unit tests on email activation.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@WebMvcTest(controllers = EmailActivationController.class)
@ActiveProfiles("test")
public class EmailActivationBusinessTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private EmailActivationBusiness emailActivationBusiness;

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
    // patchAccountActivation method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void patchAccountActivation_userExist_return302() throws Exception {
        // GIVEN
        when(emailActivationBusiness.activatedUser(any(String.class))).thenReturn(userSave);
        // WHEN
        mockMvc.perform(get("/app/register/{key")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/app/login"))
                .andDo(print());
        // THEN
        verify(emailActivationBusiness, Mockito.times(1)).activatedUser(any(String.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void patchAccountActivation_userNoExist_return302() throws Exception {
        // GIVEN
        doThrow(new MyException("throw.CustomerNotExist", ""))
                .when(emailActivationBusiness).activatedUser(any(String.class));
        // WHEN
        mockMvc.perform(get("/app/register/{key")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/app/login"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
        verify(emailActivationBusiness, Mockito.times(1)).activatedUser(any(String.class));
    }
}

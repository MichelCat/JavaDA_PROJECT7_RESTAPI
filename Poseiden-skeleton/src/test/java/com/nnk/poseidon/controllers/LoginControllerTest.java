package com.nnk.poseidon.controllers;

import com.nnk.poseidon.exception.MyExceptionBadRequestException;
import com.nnk.poseidon.business.LoginBusiness;
import com.nnk.poseidon.data.UserData;
import com.nnk.poseidon.domain.Register;
import com.nnk.poseidon.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * LoginControllerTest is a class of Endpoint unit tests on login page.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@WebMvcTest(controllers = LoginController.class)
@ActiveProfiles("test")
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private LoginBusiness loginBusiness;

    private Register registerSource;
    private MultiValueMap<String, String> registerController;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        registerSource = UserData.getRegisterSource();
        registerController = UserData.getRegisterController();
    }

    // -----------------------------------------------------------------------------------------------
    // getLogin method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void getLogin_normal_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/app/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("user", new User()))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getLogin_error_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/app/login?error=true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("error", true))
                .andExpect(model().attribute("user", new User()))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getLogin_logout_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/app/login?logout=true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("logout", true))
                .andExpect(model().attribute("user", new User()))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // getRegister method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void getRegister_normal_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/app/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("user", new Register()))
                .andDo(print());
        // THEN
    }
    // -----------------------------------------------------------------------------------------------
    // postRegister method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void postRegister_userNotExist_return302() throws Exception {
        // GIVEN
        doNothing().when(loginBusiness).addUser(any(Register.class));
        // WHEN
        mockMvc.perform(post("/app/register")
                        .with(csrf().asHeader())
                        .params(registerController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/app/login"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "USER")
    public void postRegister_userExist_return302() throws Exception {
        // GIVEN
        doThrow(new MyExceptionBadRequestException("throw.EmailAccountAlreadyExists", registerSource.getEmail()))
                .when(loginBusiness).addUser(registerSource);
        // WHEN
        mockMvc.perform(post("/app/register")
                        .with(csrf().asHeader())
                        .params(registerController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/app/register"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
    }
}

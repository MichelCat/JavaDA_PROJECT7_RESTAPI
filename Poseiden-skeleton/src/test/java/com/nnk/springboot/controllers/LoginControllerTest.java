package com.nnk.springboot.controllers;

import com.nnk.springboot.Exception.MyException;
import com.nnk.springboot.business.LoginBusiness;
import com.nnk.springboot.data.UserData;
import com.nnk.springboot.domain.Register;
import com.nnk.springboot.domain.User;
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
import org.springframework.web.context.WebApplicationContext;

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

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // -----------------------------------------------------------------------------------------------
    // GetLogin method
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
    // GetRegister method
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
    // PostRegister method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void postRegister_userNotExist_return302() throws Exception {
        // GIVEN
        doNothing().when(loginBusiness).addUser(any(Register.class));
        // WHEN
        mockMvc.perform(post("/app/register")
                        .with(csrf().asHeader())
                        .params(UserData.getRegisterController())
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
        doThrow(new MyException("throw.EmailAccountAlreadyeExists", "user@gmail.com"))
                .when(loginBusiness).addUser(UserData.getRegisterSource());
        // WHEN
        mockMvc.perform(post("/app/register")
                        .with(csrf().asHeader())
                        .params(UserData.getRegisterController())
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

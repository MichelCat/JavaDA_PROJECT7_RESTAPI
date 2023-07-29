package com.nnk.springboot.controllers;

import com.nnk.springboot.Application;
import com.nnk.springboot.data.GlobalData;
import com.nnk.springboot.data.UserData;
import com.nnk.springboot.domain.Register;
import com.nnk.springboot.domain.User;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * LoginControllerIT is a class of Endpoint integration tests on login page.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class LoginControllerIT {

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
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void postRegister_userNotExist_return302() throws Exception {
        // GIVEN
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
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    public void postRegister_userExist_return302() throws Exception {
        // GIVEN
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

    // -----------------------------------------------------------------------------------------------
    // FormLogin method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    public void postLogin_userExist_return302() throws Exception {
        mockMvc.perform(formLogin("/app/login")
                .user("username", "user@gmail.com")
                .password("password", "test")
                .acceptMediaType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void postLogin_userNotExist_return302() throws Exception {
        mockMvc.perform(formLogin("/app/login")
                .user("username", "user@gmail.com")
                .password("password", "test")
                .acceptMediaType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/app/login?error=true"))
                .andDo(print());
    }
}

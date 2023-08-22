package com.nnk.poseidon.controller;

import com.nnk.poseidon.Application;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.UserData;
import com.nnk.poseidon.model.Register;
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
import org.springframework.util.MultiValueMap;
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
class LoginControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private MultiValueMap<String, String> registerController;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        registerController = UserData.getRegisterSourceController();
    }

    // -----------------------------------------------------------------------------------------------
    // getLogin method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    void getLogin_normal_return200() throws Exception {
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
    void getLogin_error_return200() throws Exception {
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
    void getLogin_logout_return200() throws Exception {
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
    void getRegister_normal_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/app/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("register", new Register()))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // postRegister method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void postRegister_userNotExist_return302() throws Exception {
        // GIVEN
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
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    void postRegister_userExist_return302() throws Exception {
        // GIVEN
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

    // -----------------------------------------------------------------------------------------------
    // formLogin method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    void postLogin_userExist_return302() throws Exception {
        mockMvc.perform(formLogin("/app/login")
                .user("username", "user@gmail.com")
                .password("password", "12345678+aA")
                .acceptMediaType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/home"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    void postLogin_adminExist_return302() throws Exception {
        mockMvc.perform(formLogin("/app/login")
                        .user("username", "admin@gmail.com")
                        .password("password", "12345678+aA")
                        .acceptMediaType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/home"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void postLogin_userNotExist_return302() throws Exception {
        mockMvc.perform(formLogin("/app/login")
                .user("username", "user@gmail.com")
                .password("password", "12345678+aA")
                .acceptMediaType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/app/login?error=true"))
                .andDo(print());
    }
}

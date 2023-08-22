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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * UserControllerIT is a class of Endpoint integration tests on User.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
class UserControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private User userSave;
    private MultiValueMap<String, String> userSourceController;
    private MultiValueMap<String, String> userSaveController;
    private Register registerSave;
    private Register alexRegisterSave;
    private Register adminRegisterSave;
    private List<Register> registerSaves;
    private String encryptedPassword;


    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        userSave = UserData.getUserSave();
        userSourceController = UserData.getRegisterSourceController();
        userSaveController = UserData.getRegisterSaveController();

        encryptedPassword = userSave.getPassword();

        registerSaves = new ArrayList<>();

        registerSave = UserData.getRegisterSave();
        registerSave.setPassword(encryptedPassword);
        registerSaves.add(registerSave);

        alexRegisterSave = UserData.getAlexRegisterSave();
        alexRegisterSave.setPassword(encryptedPassword);
        registerSaves.add(alexRegisterSave);

        adminRegisterSave = UserData.getAdminegisterSave();
        adminRegisterSave.setPassword(encryptedPassword);
        registerSaves.add(adminRegisterSave);
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    void home_getUsers_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/user/list")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("registers", registerSaves))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // addUserForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void addUserForm_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/user/add")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().errorCount(0))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // validate method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void validate_userNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/user/validate")
                        .with(csrf().asHeader())
                        .params(userSourceController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/user/list"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    void validate_userExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/user/validate")
                        .with(csrf().asHeader())
                        .params(userSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // showUpdateForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    void showUpdateForm_userExist_return200() throws Exception {
        // GIVEN
        registerSave.setPassword("");
        // WHEN
        mockMvc.perform(get("/user/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(userSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("register", registerSave))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // updateUser method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    void updateUser_userExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/user/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(userSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/user/list"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void updateUser_userNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/user/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(userSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // deleteUser method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    void deleteUser_userExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/user/delete/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/user/list"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void deleteUser_userNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/user/delete/{id}", 1)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
    }
}

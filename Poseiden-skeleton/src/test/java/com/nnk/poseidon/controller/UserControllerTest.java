package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.UserBusiness;
import com.nnk.poseidon.data.UserData;
import com.nnk.poseidon.model.Register;
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
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * UserControllerTest is a class of Endpoint unit tests on User.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserBusiness userBusiness;

    private User userSave;

    private Register registerSource;
    private Register registerSave;
    private List<Register> registerSaves;
    private String encryptedPassword;
    private MultiValueMap<String, String> registerController;


    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        userSave = UserData.getUserSave();

        registerSource = UserData.getRegisterSource();

        encryptedPassword = userSave.getPassword();

        registerSave = UserData.getRegisterSave();
        registerSaves = new ArrayList<>();
        registerSave.setPassword(encryptedPassword);
        registerSaves.add(registerSave);

        registerController = UserData.getRegisterSourceController();
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
    void home_getUsers_return200() throws Exception {
        // GIVEN
        when(userBusiness.getUsersList()).thenReturn(registerSaves);
        // WHEN
        mockMvc.perform(get("/user/list")
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
    @WithMockUser(roles = "ADMIN")
    void addUserForm_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/user/add")
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
    @WithMockUser(roles = "ADMIN")
    void validate_userNotExist_return302() throws Exception {
        // GIVEN
        when(userBusiness.createUser(registerSource)).thenReturn(userSave);
        when(userBusiness.getUsersList()).thenReturn(registerSaves);
        // WHEN
        mockMvc.perform(post("/user/validate")
                        .with(csrf().asHeader())
                        .params(registerController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/user/list"))
                .andDo(print());
        // THEN
        verify(userBusiness, Mockito.times(1)).createUser(any(Register.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void validate_userNotValid() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/user/validate")
                        .with(csrf().asHeader())
                        .param("username", "")
                        .param("password", "")
                        .param("fullname", "")
                        .param("role", "")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(model().errorCount(4))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeHasFieldErrorCode("register","username","NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("register","fullname","NotBlank"))
//                .andExpect(model().attributeHasFieldErrorCode("register","password","PasswordConstraint"))
                .andExpect(model().attributeHasFieldErrorCode("register","password","Pattern"))
                .andDo(print());
        // THEN
        verify(userBusiness, Mockito.times(0)).createUser(any(Register.class));
    }

    // -----------------------------------------------------------------------------------------------
    // showUpdateForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
    void showUpdateForm_userExist_return200() throws Exception {
        // GIVEN
        when(userBusiness.getRegisterById(1)).thenReturn(registerSave);
        // WHEN
        mockMvc.perform(get("/user/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("register", registerSave))
                .andExpect(view().name("user/update"))
                .andDo(print());
        // THEN
        verify(userBusiness, Mockito.times(1)).getRegisterById(any(Integer.class));
    }

    // -----------------------------------------------------------------------------------------------
    // updateUser method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_userExist_return302() throws Exception {
        // GIVEN
        when(userBusiness.updateUser(1, registerSave)).thenReturn(userSave);
        when(userBusiness.getUsersList()).thenReturn(registerSaves);
        // WHEN
        mockMvc.perform(patch("/user/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(registerController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/user/list"))
                .andDo(print());
        // THEN
        verify(userBusiness, Mockito.times(1)).updateUser(any(Integer.class), any(Register.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_userNull() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/user/update/{id}", 1)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(3))
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeHasFieldErrorCode("register","username","NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("register","fullname","NotBlank"))
//                .andExpect(model().attributeHasFieldErrorCode("register","password","PasswordConstraint"))
                .andExpect(model().attributeHasFieldErrorCode("register","password","NotNull"))
                .andDo(print());
        // THEN
        verify(userBusiness, Mockito.times(0)).updateUser(any(Integer.class), any(Register.class));
    }

    void updateUser_userlank() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/user/update/{id}", 1)
                                .with(csrf().asHeader())
                        .param("username", "")
                        .param("password", "")
                        .param("fullname", "")
                        .param("role", "")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(3))
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeHasFieldErrorCode("register","username","NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("register","fullname","NotBlank"))
//                .andExpect(model().attributeHasFieldErrorCode("register","password","PasswordConstraint"))
                .andExpect(model().attributeHasFieldErrorCode("register","password","Pattern"))
                .andDo(print());
        // THEN
        verify(userBusiness, Mockito.times(0)).updateUser(any(Integer.class), any(Register.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteUser method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_userExist_return302() throws Exception {
        // GIVEN
        when(userBusiness.getUsersList()).thenReturn(registerSaves);
        doNothing().when(userBusiness).deleteUser(any(Integer.class));
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
        verify(userBusiness, Mockito.times(1)).deleteUser(any(Integer.class));
    }
}

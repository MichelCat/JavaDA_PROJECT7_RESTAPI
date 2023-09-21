package com.nnk.poseidon.controller;

import com.nnk.poseidon.Application;
import com.nnk.poseidon.Retention.TestWithMockUser;
import com.nnk.poseidon.Retention.WithMockUserRoleUser;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.RuleData;
import com.nnk.poseidon.model.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * RuleNameControllerIT is a class of Endpoint integration tests on Rule.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
class RuleNameControllerIT {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private TestWithMockUser testWithMockUser;

    public List<Rule> ruleList;

    private Rule ruleSave;
    private MultiValueMap<String, String> ruleSourceController;
    private MultiValueMap<String, String> ruleSaveController;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        testWithMockUser = new TestWithMockUser();

        ruleSave = RuleData.getRuleSave();
        ruleSourceController = RuleData.getRuleSourceController();
        ruleSaveController = RuleData.getRuleSaveController();

        ruleList = new ArrayList<>();
        ruleList.add(ruleSave);
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    void home_getRules_return200() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/ruleName/list")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("rules", ruleList))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    // -----------------------------------------------------------------------------------------------
    // addRuleForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void addRuleForm_return200() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/ruleName/add")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().errorCount(0))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    // -----------------------------------------------------------------------------------------------
    // validate method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void validate_ruleNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(post("/ruleName/validate")
                        .with(csrf().asHeader())
                        .params(ruleSourceController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    void validate_ruleExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(post("/ruleName/validate")
                        .with(csrf().asHeader())
                        .params(ruleSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    // -----------------------------------------------------------------------------------------------
    // showUpdateForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    void showUpdateForm_ruleExist_return200() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/ruleName/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(ruleSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("rule", ruleSave))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    // -----------------------------------------------------------------------------------------------
    // updateRule method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    void updateRule_ruleExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(patch("/ruleName/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(ruleSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void updateRule_ruleNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(patch("/ruleName/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(ruleSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRule method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    void deleteRule_ruleExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/ruleName/delete/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }

    @Test
    @WithMockUserRoleUser
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void deleteRule_ruleNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        ResultActions result = mockMvc.perform(get("/ruleName/delete/{id}", 1)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
        testWithMockUser.checkUserRoleUser(result);
    }
}

package com.nnk.poseidon.controller;

import com.nnk.poseidon.Application;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.RuleData;
import com.nnk.poseidon.model.Rule;
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
 * RuleNameControllerIT is a class of Endpoint integration tests on Rule.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class RuleNameControllerIT {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

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
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    public void home_getRules_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/ruleName/list")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("rules", ruleList))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // addRuleForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void addRuleForm_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/ruleName/add")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
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
    public void validate_ruleNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf().asHeader())
                        .params(ruleSourceController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    public void validate_ruleExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/ruleName/validate")
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
    }

    // -----------------------------------------------------------------------------------------------
    // showUpdateForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    public void showUpdateForm_ruleExist_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/ruleName/update/{id}", 1)
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
    }

    // -----------------------------------------------------------------------------------------------
    // updateRule method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    public void updateRule_ruleExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/ruleName/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(ruleSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void updateRule_ruleNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/ruleName/update/{id}", 1)
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
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRule method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    public void deleteRule_ruleExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/ruleName/delete/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void deleteRule_ruleNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/ruleName/delete/{id}", 1)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
    }
}

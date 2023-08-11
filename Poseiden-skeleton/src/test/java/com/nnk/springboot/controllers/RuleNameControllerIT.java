package com.nnk.springboot.controllers;

import com.nnk.springboot.Application;
import com.nnk.springboot.data.GlobalData;
import com.nnk.springboot.data.RuleNameData;
import com.nnk.springboot.domain.RuleName;
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
 * RuleNameControllerIT is a class of Endpoint integration tests on Rule Name.
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

    public List<RuleName> ruleNameList;

    private RuleName ruleNameSave;
    private MultiValueMap<String, String> ruleNameSourceController;
    private MultiValueMap<String, String> ruleNameSaveController;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        ruleNameSave = RuleNameData.getRuleNameSave();
        ruleNameSourceController = RuleNameData.getRuleNameSourceController();
        ruleNameSaveController = RuleNameData.getRuleNameSaveController();

        ruleNameList = new ArrayList<>();
        ruleNameList.add(ruleNameSave);
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleNameData.scriptCreateRuleName)
    public void home_getRuleNames_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/ruleName/list")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("ruleNames", ruleNameList))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // addRuleNameForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void addRuleNameForm_return200() throws Exception {
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
    public void validate_ruleNameNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf().asHeader())
                        .params(ruleNameSourceController)
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
    @Sql(scripts = RuleNameData.scriptCreateRuleName)
    public void validate_ruleNameExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf().asHeader())
                        .params(ruleNameSaveController)
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
    @Sql(scripts = RuleNameData.scriptCreateRuleName)
    public void showUpdateForm_ruleNameExist_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/ruleName/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(ruleNameSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("ruleName", ruleNameSave))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // updateRuleName method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleNameData.scriptCreateRuleName)
    public void updateRuleName_ruleNameExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/ruleName/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(ruleNameSaveController)
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
    public void updateRuleName_ruleNameNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/ruleName/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(ruleNameSaveController)
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
    // deleteRuleName method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleNameData.scriptCreateRuleName)
    public void deleteRuleName_ruleNameExist_return302() throws Exception {
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
    public void deleteRuleName_ruleNameNotExist_return302() throws Exception {
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

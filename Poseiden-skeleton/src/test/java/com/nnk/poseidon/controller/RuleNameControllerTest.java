package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.RuleNameBusiness;
import com.nnk.poseidon.data.RuleData;
import com.nnk.poseidon.model.Rule;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * RuleNameControllerTest is a class of Endpoint unit tests on Rule.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@WebMvcTest(controllers = RuleNameController.class)
@ActiveProfiles("test")
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private RuleNameBusiness ruleNameBusiness;

    public List<Rule> ruleList;

    private Rule ruleSource;
    private Rule ruleSave;
    private MultiValueMap<String, String> ruleSourceController;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        ruleSource = RuleData.getRuleSource();
        ruleSave = RuleData.getRuleSave();
        ruleSourceController = RuleData.getRuleSourceController();

        ruleList = new ArrayList<>();
        ruleList.add(ruleSave);
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void home_getRules_return200() throws Exception {
        // GIVEN
        when(ruleNameBusiness.getRulesList()).thenReturn(ruleList);
        // WHEN
        mockMvc.perform(get("/ruleName/list")
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
    public void addRuleForm_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/ruleName/add")
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
    public void validate_ruleNotExist_return302() throws Exception {
        // GIVEN
        when(ruleNameBusiness.createRule(ruleSource)).thenReturn(ruleSave);
        when(ruleNameBusiness.getRulesList()).thenReturn(ruleList);
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
        verify(ruleNameBusiness, Mockito.times(1)).createRule(any(Rule.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void validate_ruleNotValid() throws Exception {
        // GIVEN
        // WHEN
        MvcResult mvcResult = mockMvc.perform(post("/ruleName/validate")
                        .with(csrf().asHeader())
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", "")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(model().errorCount(6))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andDo(print())
                .andReturn();
        // THEN
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Name is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Description is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Json is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Template is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("SqlStr is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("SqlPart is mandatory");
        verify(ruleNameBusiness, Mockito.times(0)).createRule(any(Rule.class));
    }

    // -----------------------------------------------------------------------------------------------
    // showUpdateForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void showUpdateForm_ruleExist_return200() throws Exception {
        // GIVEN
        when(ruleNameBusiness.getRuleById(1)).thenReturn(ruleSave);
        // WHEN
        mockMvc.perform(get("/ruleName/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("rule", ruleSave))
                .andExpect(view().name("ruleName/update"))
                .andDo(print());
        // THEN
        verify(ruleNameBusiness, Mockito.times(1)).getRuleById(any(Integer.class));
    }

    // -----------------------------------------------------------------------------------------------
    // updateRule method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void updateRule_ruleExist_return302() throws Exception {
        // GIVEN
        when(ruleNameBusiness.updateRule(1, ruleSave)).thenReturn(ruleSave);
        when(ruleNameBusiness.getRulesList()).thenReturn(ruleList);
        // WHEN
        mockMvc.perform(patch("/ruleName/update/{id}", 1)
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
        verify(ruleNameBusiness, Mockito.times(1)).updateRule(any(Integer.class), any(Rule.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateRule_ruleNotValid() throws Exception {
        // GIVEN
        // WHEN
        MvcResult mvcResult = mockMvc.perform(patch("/ruleName/update/{id}", 1)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(6))
                .andExpect(view().name("ruleName/update"))
                .andDo(print())
                .andReturn();
        // THEN
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Name is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Description is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Json is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("Template is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("SqlStr is mandatory");
        assertThat(mvcResult.getResponse().getContentAsString()).contains("SqlPart is mandatory");
        verify(ruleNameBusiness, Mockito.times(0)).updateRule(any(Integer.class), any(Rule.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateRule_idZero() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/ruleName/update/{id}", 0)
                        .with(csrf().asHeader())
                        .params(ruleSourceController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("ruleName/update"))
                .andDo(print());
        // THEN
        verify(ruleNameBusiness, Mockito.times(0)).updateRule(any(Integer.class), any(Rule.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRule method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void deleteRule_ruleExist_return302() throws Exception {
        // GIVEN
        when(ruleNameBusiness.getRulesList()).thenReturn(ruleList);
        doNothing().when(ruleNameBusiness).deleteRule(any(Integer.class));
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
        verify(ruleNameBusiness, Mockito.times(1)).deleteRule(any(Integer.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void deleteRule_idZero() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/ruleName/delete/{id}", 0)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andDo(print());
        // THEN
        verify(ruleNameBusiness, Mockito.times(0)).deleteRule(any(Integer.class));
    }
}

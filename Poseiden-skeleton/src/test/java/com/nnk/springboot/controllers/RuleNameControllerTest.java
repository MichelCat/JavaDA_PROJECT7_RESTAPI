package com.nnk.springboot.controllers;

import com.nnk.springboot.business.RuleNameBusiness;
import com.nnk.springboot.data.RuleNameData;
import com.nnk.springboot.domain.RuleName;
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
 * RuleNameControllerTest is a class of Endpoint unit tests on Rule Name.
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

    public List<RuleName> ruleNameList;

    private RuleName ruleNameSource;
    private RuleName ruleNameSave;
    private MultiValueMap<String, String> ruleNameSourceController;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        ruleNameSource = RuleNameData.getRuleNameSource();
        ruleNameSave = RuleNameData.getRuleNameSave();
        ruleNameSourceController = RuleNameData.getRuleNameSourceController();

        ruleNameList = new ArrayList<>();
        ruleNameList.add(ruleNameSave);
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void home_getRuleNames_return200() throws Exception {
        // GIVEN
        when(ruleNameBusiness.getRuleNamesList()).thenReturn(ruleNameList);
        // WHEN
        mockMvc.perform(get("/ruleName/list")
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
    public void addRuleNameForm_return200() throws Exception {
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
    public void validate_ruleNameNotExist_return302() throws Exception {
        // GIVEN
        when(ruleNameBusiness.createRuleName(ruleNameSource)).thenReturn(ruleNameSave);
        when(ruleNameBusiness.getRuleNamesList()).thenReturn(ruleNameList);
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
        verify(ruleNameBusiness, Mockito.times(1)).createRuleName(any(RuleName.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void validate_ruleNameNotValid() throws Exception {
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
        verify(ruleNameBusiness, Mockito.times(0)).createRuleName(any(RuleName.class));
    }

    // -----------------------------------------------------------------------------------------------
    // showUpdateForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void showUpdateForm_ruleNameExist_return200() throws Exception {
        // GIVEN
        when(ruleNameBusiness.getRuleNameById(1)).thenReturn(ruleNameSave);
        // WHEN
        mockMvc.perform(get("/ruleName/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("ruleName", ruleNameSave))
                .andExpect(view().name("ruleName/update"))
                .andDo(print());
        // THEN
        verify(ruleNameBusiness, Mockito.times(1)).getRuleNameById(any(Integer.class));
    }

    // -----------------------------------------------------------------------------------------------
    // updateRuleName method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void updateRuleName_ruleNameExist_return302() throws Exception {
        // GIVEN
        when(ruleNameBusiness.updateRuleName(1, ruleNameSave)).thenReturn(ruleNameSave);
        when(ruleNameBusiness.getRuleNamesList()).thenReturn(ruleNameList);
        // WHEN
        mockMvc.perform(patch("/ruleName/update/{id}", 1)
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
        verify(ruleNameBusiness, Mockito.times(1)).updateRuleName(any(Integer.class), any(RuleName.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateRuleName_ruleNameNotValid() throws Exception {
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
        verify(ruleNameBusiness, Mockito.times(0)).updateRuleName(any(Integer.class), any(RuleName.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateRuleName_idZero() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/ruleName/update/{id}", 0)
                        .with(csrf().asHeader())
                        .params(ruleNameSourceController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("ruleName/update"))
                .andDo(print());
        // THEN
        verify(ruleNameBusiness, Mockito.times(0)).updateRuleName(any(Integer.class), any(RuleName.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRuleName method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    public void deleteRuleName_ruleNameExist_return302() throws Exception {
        // GIVEN
        when(ruleNameBusiness.getRuleNamesList()).thenReturn(ruleNameList);
        doNothing().when(ruleNameBusiness).deleteRuleName(any(Integer.class));
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
        verify(ruleNameBusiness, Mockito.times(1)).deleteRuleName(any(Integer.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void deleteRuleName_idZero() throws Exception {
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
        verify(ruleNameBusiness, Mockito.times(0)).deleteRuleName(any(Integer.class));
    }
}

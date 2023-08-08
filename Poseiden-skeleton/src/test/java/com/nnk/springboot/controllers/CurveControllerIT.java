package com.nnk.springboot.controllers;

import com.nnk.springboot.Application;
import com.nnk.springboot.data.CurvePointData;
import com.nnk.springboot.data.GlobalData;
import com.nnk.springboot.domain.CurvePoint;
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
 * CurveControllerIT is a class of Endpoint integration tests on Curve Point.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class CurveControllerIT {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    public List<CurvePoint> curvePointList;

    private CurvePoint curvePointSave;
    private MultiValueMap<String, String> curvePointSourceController;
    private MultiValueMap<String, String> curvePointSaveController;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        curvePointSave = CurvePointData.getCurvePointSave();
        curvePointSourceController = CurvePointData.getCurvePointSourceController();
        curvePointSaveController = CurvePointData.getCurvePointSaveController();

        curvePointList = new ArrayList<>();
        curvePointList.add(curvePointSave);
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = CurvePointData.scriptCreateCurvePoint)
    public void home_getCurvePoints_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/curvePoint/list")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("curvePoints", curvePointList))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // addCurvePointForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void addCurvePointForm_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/curvePoint/add")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
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
    public void validate_curvePointNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf().asHeader())
                        .params(curvePointSourceController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = CurvePointData.scriptCreateCurvePoint)
    public void validate_curvePointExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf().asHeader())
                        .params(curvePointSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/curvePoint/list"))
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
    @Sql(scripts = CurvePointData.scriptCreateCurvePoint)
    public void showUpdateForm_curvePointExist_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/curvePoint/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(curvePointSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().errorCount(0))
                .andExpect(model().attribute("curvePoint", curvePointSave))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // updateCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = CurvePointData.scriptCreateCurvePoint)
    public void updateCurvePoint_curvePointExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/curvePoint/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(curvePointSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void updateCurvePoint_curvePointNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/curvePoint/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(curvePointSaveController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // deleteCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = CurvePointData.scriptCreateCurvePoint)
    public void deleteCurvePoint_curvePointExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/curvePoint/delete/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andDo(print());
        // THEN
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void deleteCurvePoint_curvePointNotExist_return302() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/curvePoint/delete/{id}", 1)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andDo(print());
        // THEN
    }
}

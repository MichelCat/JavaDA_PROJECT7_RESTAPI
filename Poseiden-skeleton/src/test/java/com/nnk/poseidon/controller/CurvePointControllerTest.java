package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.CurvePointBusiness;
import com.nnk.poseidon.data.CurvePointData;
import com.nnk.poseidon.model.CurvePoint;
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
 * CurvePointControllerTest is a class of Endpoint unit tests on Curve Point.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@WebMvcTest(controllers = CurvePointController.class)
@ActiveProfiles("test")
class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CurvePointBusiness curvePointBusiness;

    public List<CurvePoint> curvePointList;

    private CurvePoint curvePointSource;
    private CurvePoint curvePointSave;
    private MultiValueMap<String, String> curvePointSourceController;

    @BeforeEach
    public void setUpBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        curvePointSource = CurvePointData.getCurvePointSource();
        curvePointSave = CurvePointData.getCurvePointSave();
        curvePointSourceController = CurvePointData.getCurvePointSourceController();

        curvePointList = new ArrayList<>();
        curvePointList.add(curvePointSave);
    }

    // -----------------------------------------------------------------------------------------------
    // home method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    void home_getCurvePoints_return200() throws Exception {
        // GIVEN
        when(curvePointBusiness.getCurvePointsList()).thenReturn(curvePointList);
        // WHEN
        mockMvc.perform(get("/curvePoint/list")
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
    void addCurvePointForm_return200() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(get("/curvePoint/add")
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
    void validate_curvePointNotExist_return302() throws Exception {
        // GIVEN
        when(curvePointBusiness.createCurvePoint(curvePointSource)).thenReturn(curvePointSave);
        // WHEN
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf().asHeader())
                        .params(curvePointSourceController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andDo(print());
        // THEN
        verify(curvePointBusiness, Mockito.times(1)).createCurvePoint(any(CurvePoint.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    void validate_curvePointNotValid() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf().asHeader())
                        .param("curveId", "")
                        .param("term", "")
                        .param("value", "")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(model().errorCount(3))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeHasFieldErrorCode("curvePoint","curveId","NotNull"))
                .andExpect(model().attributeHasFieldErrorCode("curvePoint","term","NotNull"))
                .andExpect(model().attributeHasFieldErrorCode("curvePoint","value","NotNull"))
                .andDo(print());
        // THEN
        verify(curvePointBusiness, Mockito.times(0)).createCurvePoint(any(CurvePoint.class));
    }

    // -----------------------------------------------------------------------------------------------
    // showUpdateForm method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    void showUpdateForm_curvePointExist_return200() throws Exception {
        // GIVEN
        when(curvePointBusiness.getCurvePointById(1)).thenReturn(curvePointSave);
        // WHEN
        mockMvc.perform(get("/curvePoint/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("curvePoint", curvePointSave))
                .andExpect(view().name("curvePoint/update"))
                .andDo(print());
        // THEN
        verify(curvePointBusiness, Mockito.times(1)).getCurvePointById(any(Integer.class));
    }

    // -----------------------------------------------------------------------------------------------
    // updateCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    void updateCurvePoint_curvePointExist_return302() throws Exception {
        // GIVEN
        when(curvePointBusiness.updateCurvePoint(1, curvePointSave)).thenReturn(curvePointSave);
        // WHEN
        mockMvc.perform(patch("/curvePoint/update/{id}", 1)
                        .with(csrf().asHeader())
                        .params(curvePointSourceController)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andDo(print());
        // THEN
        verify(curvePointBusiness, Mockito.times(1)).updateCurvePoint(any(Integer.class), any(CurvePoint.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateCurvePoint_curvePointNotValid() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(patch("/curvePoint/update/{id}", 1)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(3))
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeHasFieldErrorCode("curvePoint","curveId","NotNull"))
                .andExpect(model().attributeHasFieldErrorCode("curvePoint","term","NotNull"))
                .andExpect(model().attributeHasFieldErrorCode("curvePoint","value","NotNull"))
                .andDo(print());
        // THEN
        verify(curvePointBusiness, Mockito.times(0)).updateCurvePoint(any(Integer.class), any(CurvePoint.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    @WithMockUser(roles = "USER")
    void deleteCurvePoint_curvePointExist_return302() throws Exception {
        // GIVEN
        doNothing().when(curvePointBusiness).deleteCurvePoint(any(Integer.class));
        // WHEN
        mockMvc.perform(get("/curvePoint/delete/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andDo(print());
        // THEN
        verify(curvePointBusiness, Mockito.times(1)).deleteCurvePoint(any(Integer.class));
    }
}

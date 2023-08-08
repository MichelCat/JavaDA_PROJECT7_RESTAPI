package com.nnk.springboot.business;

import com.nnk.springboot.exception.MyExceptionBadRequestException;
import com.nnk.springboot.exception.MyExceptionNotFoundException;
import com.nnk.springboot.data.CurvePointData;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * CurveBusinessTest is a unit testing class of the Curve Points service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CurveBusinessTest {

    @Autowired
    private CurveBusiness curveBusiness;

    @MockBean
    private CurvePointRepository curvePointRepository;

    private CurvePoint curvePointSource;
    private CurvePoint curvePointSave;
    public List<CurvePoint> curvePointList;


    @BeforeEach
    public void setUpBefore() {
        curvePointSource = CurvePointData.getCurvePointSource();
        curvePointSave = CurvePointData.getCurvePointSave();

        curvePointList = new ArrayList<>();
        curvePointList.add(curvePointSave);
    }

    // -----------------------------------------------------------------------------------------------
    // getCurvePointsList method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void getCurvePointsList_findAllNormal() {
        // GIVEN
        when(curvePointRepository.findAll()).thenReturn(curvePointList);
        // WHEN
        assertThat(curveBusiness.getCurvePointsList()).isEqualTo(curvePointList);
        // THEN
        verify(curvePointRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getCurvePointsList_findAllEmpty() {
        // GIVEN
        when(curvePointRepository.findAll()).thenReturn(new ArrayList<>());
        // WHEN
        assertThat(curveBusiness.getCurvePointsList()).isEmpty();
        // THEN
        verify(curvePointRepository, Mockito.times(1)).findAll();
    }

    // -----------------------------------------------------------------------------------------------
    // createCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void createCurvePoint_saveNormal() {
        // GIVEN
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(curvePointRepository.save(curvePointSource)).thenReturn(curvePointSave);
        // WHEN
        assertThat(curveBusiness.createCurvePoint(curvePointSource)).isEqualTo(curvePointSave);
        // THEN
        verify(curvePointRepository, Mockito.times(1)).save(any(CurvePoint.class));
    }

    @Test
    public void createCurvePoint_nullCurvePointParameter_returnNullPointer() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> curveBusiness.createCurvePoint(null));
        // THEN
        verify(curvePointRepository, Mockito.times(0)).save(any(CurvePoint.class));
    }

    @Test
    public void createCurvePoint_CurvePointExist_returnBadRequest() {
        // GIVEN
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(curvePointSave));
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> curveBusiness.createCurvePoint(curvePointSave));
        // THEN
        verify(curvePointRepository, Mockito.times(0)).save(any(CurvePoint.class));
    }

    // -----------------------------------------------------------------------------------------------
    // getCurvePointById method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void getCurvePointById_findByIdNormal() {
        // GIVEN
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(curvePointSave));
        // WHEN
        assertThat(curveBusiness.getCurvePointById(1)).isEqualTo(curvePointSave);
        // THEN
        verify(curvePointRepository, Mockito.times(1)).findById(any(Integer.class));
    }

    @Test
    public void getCurvePointById_nullIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> curveBusiness.getCurvePointById(null));
        // THEN
        verify(curvePointRepository, Mockito.times(1)).findById(null);
    }

    // -----------------------------------------------------------------------------------------------
    // updateCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void updateCurvePoint_updateNormal() {
        // GIVEN
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(curvePointSave));
        when(curvePointRepository.save(curvePointSave)).thenReturn(curvePointSave);
        // WHEN
        assertThat(curveBusiness.updateCurvePoint(1, curvePointSource)).isEqualTo(curvePointSave);
        // THEN
        verify(curvePointRepository, Mockito.times(1)).save(any(CurvePoint.class));
    }

    @Test
    public void updateCurvePoint_CurvePointNotExist_returnNotFound() {
        // GIVEN
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> curveBusiness.updateCurvePoint(2, curvePointSource));
        // THEN
        verify(curvePointRepository, Mockito.times(0)).save(any(CurvePoint.class));
    }

    @Test
    public void updateCurvePoint_nullIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> curveBusiness.updateCurvePoint(null, curvePointSource));
        // THEN
        verify(curvePointRepository, Mockito.times(0)).save(any(CurvePoint.class));
    }

    @Test
    public void updateCurvePoint_zeroIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> curveBusiness.updateCurvePoint(0, curvePointSource));
        // THEN
        verify(curvePointRepository, Mockito.times(0)).save(any(CurvePoint.class));
    }

    @Test
    public void updateCurvePoint_nullCurvePointParameter_returnNotFound() {
        // GIVEN
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(curvePointSave));
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> curveBusiness.updateCurvePoint(1, null));
        // THEN
        verify(curvePointRepository, Mockito.times(0)).save(any(CurvePoint.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void deleteCurvePoint_deleteNormal() {
        // GIVEN
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(curvePointSave));
        doNothing().when(curvePointRepository).deleteById(any(Integer.class));
        // WHEN
        curveBusiness.deleteCurvePoint(1);
        // THEN
        verify(curvePointRepository, Mockito.times(1)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteCurvePoint_CurvePointNotExist_returnNotFound() {
        // GIVEN
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> curveBusiness.deleteCurvePoint(2));
        // THEN
        verify(curvePointRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteCurvePoint_nullIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> curveBusiness.deleteCurvePoint(null));
        // THEN
        verify(curvePointRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteCurvePoint_zeroIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> curveBusiness.deleteCurvePoint(0));
        // THEN
        verify(curvePointRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }
}

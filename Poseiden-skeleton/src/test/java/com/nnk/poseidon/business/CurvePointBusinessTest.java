package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.data.CurvePointData;
import com.nnk.poseidon.model.CurvePoint;
import com.nnk.poseidon.repository.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
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
 * CurvePointBusinessTest is a unit testing class of the Curve Points service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class CurvePointBusinessTest {

    @Autowired
    private CurvePointBusiness curvePointBusiness;
    @Autowired
    private MessageSource messageSource;

    @MockBean
    private CurvePointRepository curvePointRepository;

    private TestMessageSource testMessageSource;
    private CurvePoint curvePointSource;
    private CurvePoint curvePointSave;
    public List<CurvePoint> curvePointList;


    @BeforeEach
    public void setUpBefore() {
        testMessageSource = new TestMessageSource(messageSource);

        curvePointSource = CurvePointData.getCurvePointSource();
        curvePointSave = CurvePointData.getCurvePointSave();

        curvePointList = new ArrayList<>();
        curvePointList.add(curvePointSave);
    }

    // -----------------------------------------------------------------------------------------------
    // getCurvePointsList method
    // -----------------------------------------------------------------------------------------------
    @Test
    void getCurvePointsList_findAllNormal() {
        // GIVEN
        when(curvePointRepository.findAll()).thenReturn(curvePointList);
        // WHEN
        assertThat(curvePointBusiness.getCurvePointsList()).isEqualTo(curvePointList);
        // THEN
        verify(curvePointRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getCurvePointsList_findAllEmpty() {
        // GIVEN
        when(curvePointRepository.findAll()).thenReturn(new ArrayList<>());
        // WHEN
        assertThat(curvePointBusiness.getCurvePointsList()).isEmpty();
        // THEN
        verify(curvePointRepository, Mockito.times(1)).findAll();
    }

    // -----------------------------------------------------------------------------------------------
    // createCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    void createCurvePoint_saveNormal() throws MyException {
        // GIVEN
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(curvePointRepository.save(curvePointSource)).thenReturn(curvePointSave);
        // WHEN
        assertThat(curvePointBusiness.createCurvePoint(curvePointSource)).isEqualTo(curvePointSave);
        // THEN
        verify(curvePointRepository, Mockito.times(1)).save(any(CurvePoint.class));
    }

    @Test
    void createCurvePoint_nullCurvePointParameter_returnNullPointer() {
        // GIVEN
        when(curvePointRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.createCurvePoint(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.curvePoint.nullCurvePoint", null);
        verify(curvePointRepository, Mockito.times(0)).save(any(CurvePoint.class));
    }

    @Test
    void createCurvePoint_CurvePointExist_returnMyException() {
        // GIVEN
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(curvePointSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.createCurvePoint(curvePointSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.curvePoint.curvePointExists", new Object[] { curvePointSave.getId() });
        verify(curvePointRepository, Mockito.times(0)).save(any(CurvePoint.class));
    }

    // -----------------------------------------------------------------------------------------------
    // getCurvePointById method
    // -----------------------------------------------------------------------------------------------
    @Test
    void getCurvePointById_findByIdNormal() throws MyException {
        // GIVEN
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(curvePointSave));
        // WHEN
        assertThat(curvePointBusiness.getCurvePointById(1)).isEqualTo(curvePointSave);
        // THEN
        verify(curvePointRepository, Mockito.times(1)).findById(any(Integer.class));
    }

    @Test
    void getCurvePointById_nullIdParameter_returnMyException() {
        // GIVEN
        when(curvePointRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.getCurvePointById(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.curvePoint.unknown", new Object[] { null });
        verify(curvePointRepository, Mockito.times(1)).findById(null);
    }

    // -----------------------------------------------------------------------------------------------
    // updateCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    void updateCurvePoint_updateNormal() throws MyException {
        // GIVEN
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(curvePointSave));
        when(curvePointRepository.save(curvePointSave)).thenReturn(curvePointSave);
        // WHEN
        assertThat(curvePointBusiness.updateCurvePoint(1, curvePointSource)).isEqualTo(curvePointSave);
        // THEN
        verify(curvePointRepository, Mockito.times(1)).save(any(CurvePoint.class));
    }

    @Test
    void updateCurvePoint_CurvePointNotExist_returnMyException() {
        // GIVEN
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.updateCurvePoint(2, curvePointSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.curvePoint.unknown", new Object[] { 2 });
        verify(curvePointRepository, Mockito.times(0)).save(any(CurvePoint.class));
    }

    @Test
    void updateCurvePoint_nullIdParameter_returnMyException() {
        // GIVEN
        when(curvePointRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.updateCurvePoint(null, curvePointSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.curvePoint.unknown", new Object[] { null });
        verify(curvePointRepository, Mockito.times(0)).save(any(CurvePoint.class));
    }

    @Test
    void updateCurvePoint_zeroIdParameter_returnMyException() {
        // GIVEN
        when(curvePointRepository.findById(0)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.updateCurvePoint(0, curvePointSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.curvePoint.unknown", new Object[] { 0 });
        verify(curvePointRepository, Mockito.times(0)).save(any(CurvePoint.class));
    }

    @Test
    void updateCurvePoint_nullCurvePointParameter_returnMyException() {
        // GIVEN
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(curvePointSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.updateCurvePoint(1, null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.curvePoint.nullCurvePoint", null);
        verify(curvePointRepository, Mockito.times(0)).save(any(CurvePoint.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    void deleteCurvePoint_deleteNormal() throws MyException {
        // GIVEN
        when(curvePointRepository.existsById(any(Integer.class))).thenReturn(true);
        doNothing().when(curvePointRepository).deleteById(any(Integer.class));
        // WHEN
        curvePointBusiness.deleteCurvePoint(1);
        // THEN
        verify(curvePointRepository, Mockito.times(1)).deleteById(any(Integer.class));
    }

    @Test
    void deleteCurvePoint_CurvePointNotExist_returnMyException() {
        // GIVEN
        when(curvePointRepository.existsById(any(Integer.class))).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.deleteCurvePoint(2));
        // THEN
        testMessageSource.compare(exception
                , "exception.curvePoint.unknown", new Object[] { 2 });
        verify(curvePointRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    void deleteCurvePoint_nullIdParameter_returnMyException() {
        // GIVEN
        when(curvePointRepository.existsById(null)).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.deleteCurvePoint(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.curvePoint.unknown", new Object[] { null });
        verify(curvePointRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    void deleteCurvePoint_zeroIdParameter_returnMyException() {
        // GIVEN
        when(curvePointRepository.existsById(0)).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.deleteCurvePoint(0));
        // THEN
        testMessageSource.compare(exception
                            , "exception.curvePoint.unknown", new Object[] { 0 });
       verify(curvePointRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }
}

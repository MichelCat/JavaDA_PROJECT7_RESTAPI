package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.data.CurvePointData;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.model.CurvePoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * CurvePointBusinessIT is a class of integration tests on Curve Points service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CurvePointBusinessIT {

    @Autowired
    private CurvePointBusiness curvePointBusiness;
    @Autowired
    private MessageSource messageSource;

    private TestMessageSource testMessageSource;
    private CurvePoint curvePointSource;
    private CurvePoint curvePointSave;
    private Integer curvePointId;


    @BeforeEach
    public void setUpBefore() {
        testMessageSource = new TestMessageSource(messageSource);

        curvePointSource = CurvePointData.getCurvePointSource();
        curvePointSave = CurvePointData.getCurvePointSave();

        curvePointId = curvePointSave.getId();
    }

    // -----------------------------------------------------------------------------------------------
    // getCurvePointsList method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = CurvePointData.scriptCreateCurvePoint)
    void getCurvePointsList_findAllNormal() {
        // GIVEN
        // WHEN
        List<CurvePoint> result = curvePointBusiness.getCurvePointsList();
        // THEN
        assertThat(result).hasSize(1)
                            .contains(curvePointSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void getCurvePointsList_findAllEmpty() {
        // GIVEN
        // WHEN
        List<CurvePoint> result = curvePointBusiness.getCurvePointsList();
        // THEN
        assertThat(result).isEmpty();
    }

    // -----------------------------------------------------------------------------------------------
    // createCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void createCurvePoint_curvePointNotExist() throws MyException {
        // GIVEN
        // WHEN
        CurvePoint result = curvePointBusiness.createCurvePoint(curvePointSource);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("creationDate")
                .isEqualTo(curvePointSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = CurvePointData.scriptCreateCurvePoint)
    void createCurvePoint_curvePointExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.createCurvePoint(curvePointSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.curvePoint.curvePointExists", new Object[] { curvePointSave.getId() });
    }

    // -----------------------------------------------------------------------------------------------
    // getCurvePointById method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = CurvePointData.scriptCreateCurvePoint)
    void getCurvePointById_curvePointExist() throws MyException {
        // GIVEN
        // WHEN
        assertThat(curvePointBusiness.getCurvePointById(curvePointId)).isEqualTo(curvePointSave);
        // THEN
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void getCurvePointById_curvePointNotExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.getCurvePointById(curvePointId));
        // THEN
        testMessageSource.compare(exception
                            , "exception.curvePoint.unknown", new Object[] { curvePointId });
   }

    // -----------------------------------------------------------------------------------------------
    // updateCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = CurvePointData.scriptCreateCurvePoint)
    void updateCurvePoint_curvePointExist() throws MyException {
        // GIVEN
        // WHEN
        CurvePoint result = curvePointBusiness.updateCurvePoint(curvePointId, curvePointSave);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("asOfDate")
                .isEqualTo(curvePointSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void updateCurvePoint_curvePointNotExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.updateCurvePoint(curvePointId, curvePointSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.curvePoint.unknown", new Object[] { curvePointId });
    }

    // -----------------------------------------------------------------------------------------------
    // deleteCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = CurvePointData.scriptCreateCurvePoint)
    void deleteCurvePoint_curvePointExist() throws MyException {
        // GIVEN
        // WHEN
        curvePointBusiness.deleteCurvePoint(curvePointId);
        // THEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.getCurvePointById(curvePointId));
        testMessageSource.compare(exception
                            , "exception.curvePoint.unknown", new Object[] { curvePointId });
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void deleteCurvePoint_curvePointNotExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> curvePointBusiness.deleteCurvePoint(curvePointId));
        // THEN
        testMessageSource.compare(exception
                            , "exception.curvePoint.unknown", new Object[] { curvePointId });
    }
}

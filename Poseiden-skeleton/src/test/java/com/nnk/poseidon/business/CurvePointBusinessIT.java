package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyExceptionBadRequestException;
import com.nnk.poseidon.exception.MyExceptionNotFoundException;
import com.nnk.poseidon.data.CurvePointData;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.domain.CurvePoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
public class CurvePointBusinessIT {

    @Autowired
    private CurvePointBusiness curvePointBusiness;

    private CurvePoint curvePointSource;
    private CurvePoint curvePointSave;


    @BeforeEach
    public void setUpBefore() {
        curvePointSource = CurvePointData.getCurvePointSource();
        curvePointSave = CurvePointData.getCurvePointSave();
    }

    // -----------------------------------------------------------------------------------------------
    // getCurvePointsList method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = CurvePointData.scriptCreateCurvePoint)
    public void getCurvePointsList_findAllNormal() {
        // GIVEN
        // WHEN
        List<CurvePoint> result = curvePointBusiness.getCurvePointsList();
        // THEN
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(curvePointSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void getCurvePointsList_findAllEmpty() {
        // GIVEN
        // WHEN
        List<CurvePoint> result = curvePointBusiness.getCurvePointsList();
        // THEN
        assertThat(result.size()).isEqualTo(0);
    }

    // -----------------------------------------------------------------------------------------------
    // createCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void createCurvePoint_curvePointNotExist() {
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
    public void createCurvePoint_curvePointExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> curvePointBusiness.createCurvePoint(curvePointSave));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // getCurvePointById method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = CurvePointData.scriptCreateCurvePoint)
    public void getCurvePointById_curvePointExist() {
        // GIVEN
        // WHEN
        assertThat(curvePointBusiness.getCurvePointById(curvePointSave.getId())).isEqualTo(curvePointSave);
        // THEN
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void getCurvePointById_curvePointNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> curvePointBusiness.getCurvePointById(curvePointSave.getId()));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // updateCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = CurvePointData.scriptCreateCurvePoint)
    public void updateCurvePoint_curvePointExist() {
        // GIVEN
        // WHEN
        CurvePoint result = curvePointBusiness.updateCurvePoint(curvePointSave.getId(), curvePointSave);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("asOfDate")
                .isEqualTo(curvePointSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void updateCurvePoint_curvePointNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> curvePointBusiness.updateCurvePoint(curvePointSave.getId(), curvePointSave));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // deleteCurvePoint method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = CurvePointData.scriptCreateCurvePoint)
    public void deleteCurvePoint_curvePointExist() {
        // GIVEN
        // WHEN
        curvePointBusiness.deleteCurvePoint(curvePointSave.getId());
        // THEN
        assertThrows(MyExceptionNotFoundException.class, () -> curvePointBusiness.getCurvePointById(curvePointSave.getId()));
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void deleteCurvePoint_curvePointNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> curvePointBusiness.deleteCurvePoint(curvePointSave.getId()));
        // THEN
    }
}

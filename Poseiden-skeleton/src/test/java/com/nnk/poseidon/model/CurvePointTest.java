package com.nnk.poseidon.model;

import com.nnk.poseidon.data.CurvePointData;
import com.nnk.poseidon.data.GlobalData;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * CurvePointTest is the unit test class managing the CurvePoint
 *
 * @author MC
 * @version 1.0
 */
@SpringBootTest
@ActiveProfiles("test")
class CurvePointTest {

    @Autowired
    private Validator validator;

    private TestConstraintViolation<CurvePoint> testConstraintViolation;
    private CurvePoint curvePoint;
    private Timestamp currentTimestamp;

    @BeforeEach
    private void setUpBefore() {
        currentTimestamp = GlobalData.CURRENT_TIMESTAMP;

        testConstraintViolation = new TestConstraintViolation<>(validator);

        curvePoint = CurvePointData.getCurvePointSource();
    }

    // -----------------------------------------------------------------------------------------------
    // builder method
    // -----------------------------------------------------------------------------------------------
    @Test
    void builder_TestBuildAndNew_thenEqual() {
        // GIVEN
        // WHEN
        CurvePoint objBuild = CurvePoint.builder()
                                .build();
        CurvePoint objNew = new CurvePoint();
        // THEN
        assertThat(objBuild).isEqualTo(objNew);
    }


    // -----------------------------------------------------------------------------------------------
    // curveId attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void curveId_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        curvePoint.setCurveId(10);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(curvePoint, errorList);
        assertThat(curvePoint.getCurveId()).isEqualTo(10);
    }

    @Test
    void curveId_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        curvePoint.setCurveId(null);
        // THEN
        String[][] errorList = {{"curveId", "{constraint.notNull.curvePoint.curveId}"}};
        testConstraintViolation.checking(curvePoint, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // asOfDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void asOfDate_normal() {
        // GIVEN
        // WHEN
        curvePoint.setAsOfDate(currentTimestamp);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(curvePoint, errorList);
        assertThat(curvePoint.getCreationDate()).isEqualTo(currentTimestamp);
    }

    // -----------------------------------------------------------------------------------------------
    // term attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void term_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        curvePoint.setTerm(10d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(curvePoint, errorList);
        assertThat(curvePoint.getTerm()).isEqualTo(10d);
    }

    @Test
    void term_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        curvePoint.setTerm(null);
        // THEN
        String[][] errorList = {{"term", "{constraint.notNull.curvePoint.term}"}};
        testConstraintViolation.checking(curvePoint, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // value attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void value_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        curvePoint.setValue(30d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(curvePoint, errorList);
        assertThat(curvePoint.getValue()).isEqualTo(30d);
    }

    @Test
    void value_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        curvePoint.setValue(null);
        // THEN
        String[][] errorList = {{"value", "{constraint.notNull.curvePoint.value}"}};
        testConstraintViolation.checking(curvePoint, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // creationDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void creationDate_normal() {
        // GIVEN
        // WHEN
        curvePoint.setCreationDate(currentTimestamp);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(curvePoint, errorList);
        assertThat(curvePoint.getCreationDate()).isEqualTo(currentTimestamp);
    }
}

package com.nnk.springboot.domain;

import com.nnk.springboot.data.CurvePointData;
import com.nnk.springboot.data.GlobalData;
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
public class CurvePointTest {

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
    public void builder_TestBuildAndNew_thenEqual() {
        // GIVEN
        // WHEN
        CurvePoint objBuild = CurvePoint.builder()
                                .build();
        CurvePoint objNew = new CurvePoint();
        // THEN
        assertThat(objBuild).usingRecursiveComparison().isEqualTo(objNew);
    }


    // -----------------------------------------------------------------------------------------------
    // curveId attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void curveId_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        curvePoint.setCurveId(10);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(curvePoint, errorList);
        assertThat(curvePoint.getCurveId()).isEqualTo(10);
    }

    @Test
    public void curveId_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        curvePoint.setCurveId(null);
        // THEN
        String[][] errorList = {{"curveId", "Curve Id must not be null"}};
        testConstraintViolation.checking(curvePoint, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // asOfDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void asOfDate_normal() {
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
    public void term_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        curvePoint.setTerm(10d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(curvePoint, errorList);
        assertThat(curvePoint.getTerm()).isEqualTo(10d);
    }

    @Test
    public void term_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        curvePoint.setTerm(null);
        // THEN
        String[][] errorList = {{"term", "Term must not be null"}};
        testConstraintViolation.checking(curvePoint, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // value attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void value_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        curvePoint.setValue(30d);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(curvePoint, errorList);
        assertThat(curvePoint.getValue()).isEqualTo(30d);
    }

    @Test
    public void value_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        curvePoint.setValue(null);
        // THEN
        String[][] errorList = {{"value", "Value must not be null"}};
        testConstraintViolation.checking(curvePoint, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // creationDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void creationDate_normal() {
        // GIVEN
        // WHEN
        curvePoint.setCreationDate(currentTimestamp);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(curvePoint, errorList);
        assertThat(curvePoint.getCreationDate()).isEqualTo(currentTimestamp);
    }
}

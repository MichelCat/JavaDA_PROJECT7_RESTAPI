package com.nnk.springboot.domain;

import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
public class CurvePointTest {

    @Autowired
    private Validator validator;

    private TestConstraintViolation<CurvePoint> testConstraintViolation;
    private CurvePoint curvePoint;

    @BeforeEach
    private void setUpBefore() {
        testConstraintViolation = new TestConstraintViolation<>(validator);

        curvePoint = CurvePoint.builder()
                .curveId(10)
                .term(10d)
                .value(30d)
                .build();
    }

    // -----------------------------------------------------------------------------------------------
    // CurveId attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalCurveId_thenNoConstraintViolations() {
        // GIVEN
        curvePoint.setCurveId(10);
        // WHEN
        String[][] errorList = {};
        testConstraintViolation.checking(curvePoint, errorList);
    }

    @Test
    public void whenNullCurveId_thenOneConstraintViolation() {
        // GIVEN
        curvePoint.setCurveId(null);
        // WHEN
        String[][] errorList = {{"curveId", "Curve Id cannot be null"}};
        testConstraintViolation.checking(curvePoint, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // Term attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalTerm_thenNoConstraintViolations() {
        // GIVEN
        curvePoint.setTerm(10d);
        // WHEN
        String[][] errorList = {};
        testConstraintViolation.checking(curvePoint, errorList);
    }

    @Test
    public void whenNullTerm_thenOneConstraintViolation() {
        // GIVEN
        curvePoint.setTerm(null);
        // WHEN
        String[][] errorList = {{"term", "Term cannot be null"}};
        testConstraintViolation.checking(curvePoint, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // Value attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalValue_thenNoConstraintViolations() {
        // GIVEN
        curvePoint.setValue(30d);
        // WHEN
        String[][] errorList = {};
        testConstraintViolation.checking(curvePoint, errorList);
    }

    @Test
    public void whenNullValue_thenOneConstraintViolation() {
        // GIVEN
        curvePoint.setValue(null);
        // WHEN
        String[][] errorList = {{"value", "Value cannot be null"}};
        testConstraintViolation.checking(curvePoint, errorList);
    }
}

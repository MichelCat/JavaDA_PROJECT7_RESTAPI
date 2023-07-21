package com.nnk.springboot.domain;

import com.nnk.springboot.Exception.MyExceptionBadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TestConstraintViolation is the test class for constraint violations of an attribute of a class
 *
 * @author MC
 * @version 1.0
 */
public class TestConstraintViolation<T> {

    private Validator validator;

    public TestConstraintViolation(Validator validator) {
        this.validator = validator;
    }

    /**
     * Processing an attribute that had no constraint violations
     *
     * @param object Object to be tested
     */
    public void noConstraintViolation(T object) {
        // THEN
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        assertThat(violations.size()).isEqualTo(0);
    }

    /**
     * Processing an attribute that had constraint violations
     *
     * @param object Object to be tested
     * @param attribute Attribute name
     * @param message Error message
     */
    public void oneConstraintViolation(T object, String attribute, String message) {
        // THEN
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo(attribute);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(message);
    }
}

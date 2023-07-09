package com.nnk.springboot.domain;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TestConstraintViolation<T> {

    private Validator validator;

    public TestConstraintViolation(Validator validator) {
        this.validator = validator;
    }

    public void noConstraintViolation(T object) {
        // WHEN
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        // THEN
        assertThat(violations.size()).isEqualTo(0);
    }

    public void oneConstraintViolation(T object, String attribute, String message) {
        // WHEN
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        // THEN
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo(attribute);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(message);
    }
}

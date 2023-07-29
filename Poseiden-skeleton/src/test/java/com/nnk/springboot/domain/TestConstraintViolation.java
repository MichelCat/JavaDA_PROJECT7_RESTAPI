package com.nnk.springboot.domain;

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
     * Processing an attribute that had constraint violations
     *
     * @param object Object to be tested
     * @param errorList Error list
     */
    public void checking(T object, String[][] errorList) {
        // THEN
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        for (ConstraintViolation violation : violations) {
System.out.println("Property: " + violation.getPropertyPath());
System.out.println("Message: " + violation.getMessage());
System.out.println("Invalid value: " + violation.getInvalidValue());
System.out.println("------------------------");

            String attribute = "";
            String message = "";
            for (String errorElement[] : errorList) {
                if (violation.getPropertyPath().toString().equals(errorElement[0])
                        && violation.getMessage().toString().equals(errorElement[1])) {
                    attribute = errorElement[0];
                    message = errorElement[1];
                    break;
                }
            }
System.out.println(attribute);
System.out.println(message);
            assertThat(violation.getPropertyPath().toString()).isEqualTo(attribute);
            assertThat(violation.getMessage()).isEqualTo(message);
        }

        assertThat(violations.size()).isEqualTo(errorList.length);
    }
}

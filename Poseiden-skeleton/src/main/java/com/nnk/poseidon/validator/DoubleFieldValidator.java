package com.nnk.poseidon.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DoubleFieldValidator implements ConstraintValidator<DoubleFieldConstraint, Double> {

    // A decimal digit consisting of only digits and a comma.
    private static final String DOUBLE_FIELD_PATTERN = "^[+-]?\\d+([.]\\d*)?$";

    @Override
    public void initialize(DoubleFieldConstraint doubleField) {
    }

    @Override
    public boolean isValid(Double doubleField
                            , ConstraintValidatorContext cxt) {
        // Null processed by @NotNull
        if (doubleField == null) {
            return true;
        }
        String stringValue = String.valueOf(doubleField);
        return stringValue.matches(DOUBLE_FIELD_PATTERN);
    }
}

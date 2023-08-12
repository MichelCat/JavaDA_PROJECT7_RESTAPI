package com.nnk.poseidon.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    // The password (at least one capital letter, at least one number, at least one symbol, at least 8 characters)
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$";

    @Override
    public void initialize(PasswordConstraint password) {
    }

    @Override
    public boolean isValid(String password
                           , ConstraintValidatorContext cxt) {
        return password != null && password.matches(PASSWORD_PATTERN);
    }
}
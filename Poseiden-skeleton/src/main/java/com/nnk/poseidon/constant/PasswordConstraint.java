package com.nnk.poseidon.constant;

public class PasswordConstraint {

    // The password (at least one capital letter, at least one number, at least one symbol, at least 8 characters)
    public static final String REGEXP = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$";

    public static final String MESSAGE = "{constraint.passwordConstraint.global}";
}

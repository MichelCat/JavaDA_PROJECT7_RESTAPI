package com.nnk.poseidon.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "{constraint.passwordConstraint.global}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
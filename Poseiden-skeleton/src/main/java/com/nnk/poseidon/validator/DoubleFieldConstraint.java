package com.nnk.poseidon.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DoubleFieldValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleFieldConstraint {
    String message() default "A decimal digit consisting of only digits and a comma.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

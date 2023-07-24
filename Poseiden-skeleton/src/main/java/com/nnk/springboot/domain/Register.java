package com.nnk.springboot.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

/**
 * Register is business model
 *
 * @author MC
 * @version 1.0
 */
@Validated
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"email", "password", "firstName", "lastName"})
@ToString
public class Register {

    /**
     * User email
     */
    String email;

    /**
     * User password
     */
    @ToString.Exclude
    String password;

    /**
     * Customer first name
     */
    String firstName;

    /**
     * Customer last name
     */
    String lastName;

    public Register() {
        email = "";
        password = "";
        firstName = "";
        lastName = "";
    }
}

package com.nnk.springboot.domain;

import lombok.*;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
     * User first name
     */
    String firstName;

    /**
     * User last name
     */
    String lastName;

//    public Register() {
//        email = "";
//        password = "";
//        firstName = "";
//        lastName = "";
//    }
}

package com.nnk.springboot.domain;

import com.nnk.springboot.validator.PasswordConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Register is business model
 *
 * @author MC
 * @version 1.0
 */
//@Validated
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Register {

    /**
     * User email
     */
    @NotBlank(message = "Username is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    @Email(message = "Email should be valid")
    String email;

    /**
     * User password
     */
    @ToString.Exclude
    @PasswordConstraint
    String password;

    /**
     * Full name
     */
    @NotBlank(message = "FullName is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String fullname;
}

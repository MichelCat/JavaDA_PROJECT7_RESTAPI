package com.nnk.poseidon.model;

import com.nnk.poseidon.validator.PasswordConstraint;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
     * User ID
     */
    Integer id;

    /**
     * Email used to authenticate the user
     */
    @NotBlank(message = "Username is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    @Email(message = "Email should be valid")
    String username;

    /**
     * User password
     */
    @ToString.Exclude
    @PasswordConstraint
    @Size(max = 125, message = "Maximum length of {max} characters")
    String password;

    /**
     * Full name
     */
    @NotBlank(message = "FullName is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String fullname;

    /**
     * User role
     */
//    @NotNull(message = "Role must not be null")
    @Enumerated(value = EnumType.STRING)
    Role role;
}

package com.nnk.poseidon.model;

import com.nnk.poseidon.validator.PasswordConstraint;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
     * User ID
     */
    Integer id;

    /**
     * Email used to authenticate the user
     */
    @NotBlank(message = "{constraint.notBlank.register.username}")
    @Size(max = 125, message = "{constraint.size.global}")
    @Email(message = "{constraint.email.register.username}")
    String username;

    /**
     * User password
     */
    @ToString.Exclude
    @PasswordConstraint
    @Size(max = 125, message = "{constraint.size.global}")
    String password;

    /**
     * Full name
     */
    @NotBlank(message = "{constraint.notBlank.register.fullname}")
    @Size(max = 125, message = "{constraint.size.global}")
    String fullname;

    /**
     * User role
     */
    @Enumerated(value = EnumType.STRING)
    Role role;
}

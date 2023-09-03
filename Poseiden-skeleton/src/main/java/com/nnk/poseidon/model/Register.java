package com.nnk.poseidon.model;

import com.nnk.poseidon.constant.PasswordConstraint;
import com.nnk.poseidon.enumerator.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Register is business model
 *
 * @author MC
 * @version 1.0
 */
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
//    @PasswordConstraint
    @NotNull(message = PasswordConstraint.MESSAGE)
    @Pattern(regexp = PasswordConstraint.REGEXP, message = PasswordConstraint.MESSAGE)
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
    UserRole role;
}

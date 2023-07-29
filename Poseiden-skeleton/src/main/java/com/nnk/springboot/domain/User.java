package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * User is entity model
 *
 * @author MC
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "users")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * User ID
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    /**
     * Email used to authenticate the user
     */
    @NotBlank(message = "Username is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    @Email(message = "Email should be valid")
    @Column(unique=true)
    String username;

    /**
     * Password used to authenticate the user
     */
    @ToString.Exclude
    @NotBlank(message = "Password is mandatory")
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
    @NotBlank(message = "Role is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String role;

    /**
     * User account expired
     */
    @NotNull(message = "User account expired cannot be null")
    Boolean expired;

    /**
     * User locked
     */
    @NotNull(message = "User locked cannot be null")
    Boolean locked;

    /**
     * User credentials (password) expired
     */
    @NotNull(message = "User credentials expired cannot be null")
    Boolean credentialsExpired;

    /**
     * Activated user
     */
    @NotNull(message = "Activated user cannot be null")
    Boolean enabled;

    /**
     * Email validation key for user
     */
    @Column(unique=true)
    @Size(max = 36, message = "Maximum length of {max} characters")
    String emailValidationKey;

    /**
     * Valid email end date for user
     */
    Date validEmailEndDate;


    // -----------------------------------------------------------------------------------------------
    // Authorization management
    // -----------------------------------------------------------------------------------------------
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        return Collections.singleton(authority);
    }

    @Override
    public boolean isAccountNonExpired() { return !expired; }

    @Override
    public boolean isAccountNonLocked() { return !locked; }

    @Override
    public boolean isCredentialsNonExpired() { return !credentialsExpired; }

    @Override
    public boolean isEnabled() { return enabled; }

    /**
     * Create authorization
     */
    public void createAuthorization() {
        this.expired = false;
        this.locked = false;
        this.credentialsExpired = false;
        this.enabled = true;
    }

    /**
     * Test Contains a role
     *
     * @param role Role to search
     * @return Boolean Role belongs to user
     */
    public boolean isContainsRole(String role) {
        return role.equals(role);
    }


    // -----------------------------------------------------------------------------------------------
    // Email validation
    // -----------------------------------------------------------------------------------------------
    /**
     * Test valid email key for user
     *
     * @param testKey Key to validate
     * @return Boolean Validated key
     */
    public boolean isValidEmailKey(String testKey) {
        return this.emailValidationKey.equals(testKey);
    }

    /**
     * Test valid email date for user
     *
     * @return Boolean Validated key
     */
    public boolean isValidEmailEndDate() {
        Date currentDate = new Date();
        return currentDate.before(validEmailEndDate);
    }

    /**
     * Create valid email key and valid email end date for user
     */
    public void createValidationEmail() {
        this.emailValidationKey = UUID.randomUUID().toString();

        // Today's date plus 24 hours
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        this.validEmailEndDate = calendar.getTime();
    }
}

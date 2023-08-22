package com.nnk.poseidon.model;

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
@Table(name = "user")
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
    @NotBlank(message = "{constraint.notBlank.user.username}")
    @Size(max = 125, message = "{constraint.size.global}")
    @Email(message = "{constraint.email.user.username}")
    @Column(unique=true)
    String username;

    /**
     * Password used to authenticate the user
     */
    @ToString.Exclude
    @NotBlank(message = "{constraint.notBlank.user.password}")
    @Size(max = 125, message = "{constraint.size.global}")
    String password;

    /**
     * Full name
     */
    @NotBlank(message = "{constraint.notBlank.user.fullname}")
    @Size(max = 125, message = "{constraint.size.global}")
    String fullname;

    /**
     * User role
     */
    @NotNull(message = "{constraint.notNull.user.role}")
    @Enumerated(value = EnumType.STRING)
    Role role;

    /**
     * User account expired
     */
    @NotNull(message = "{constraint.notNull.user.expired}")
    Boolean expired;

    /**
     * User locked
     */
    @NotNull(message = "{constraint.notNull.user.locked}")
    Boolean locked;

    /**
     * User credentials (password) expired
     */
    @NotNull(message = "{constraint.notNull.user.credentialsExpired}")
    Boolean credentialsExpired;

    /**
     * Activated user
     */
    @NotNull(message = "{constraint.notNull.user.enabled}")
    Boolean enabled;

    /**
     * Email validation key for user
     */
    @Column(unique=true)
    @Size(max = 36, message = "{constraint.size.global}")
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
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
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

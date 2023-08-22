package com.nnk.poseidon.data;

import com.nnk.poseidon.model.Register;
import com.nnk.poseidon.model.Role;
import com.nnk.poseidon.model.User;
import com.nnk.poseidon.mapper.MultiValueMapMapper;
import org.springframework.util.MultiValueMap;

import java.sql.Date;

/**
 * UserData is the class containing the user test data
 *
 * @author MC
 * @version 1.0
 */
public class UserData {

    private static String encryptedPassword = "$2a$10$o47/tdA8WWuD1/ZRNfwDjOiDvS2wGzgl5/jPGK7U36qkz92I9ZO/K";
    private static String unencryptedPassword = "12345678+aA";
    private static String userEmailValidationKey = "cf0551e9-1c63-4b93-ae6e-bb3966c83e83";
    private static String alexEmailValidationKey = "cf0551e9-1c63-4b93-ae6e-bb3966c83e85";

    // -----------------------------------------------------------------------------------------------
    //
    // -----------------------------------------------------------------------------------------------
    public static User getUserSource() {
        return User.builder()
                .username("user@gmail.com")
                .password(encryptedPassword)
                .fullname("User")
                .role(Role.USER)
                .expired(false)
                .locked(false)
                .credentialsExpired(false)
                .enabled(true)
                .emailValidationKey(userEmailValidationKey)
                .validEmailEndDate(new Date(123,06,29))
                .build();
    }

    public static User getUserSave() {
        User user = getUserSource();
        user.setId(1);
        return user;
    }

    // -----------------------------------------------------------------------------------------------
    //
    // -----------------------------------------------------------------------------------------------
    public static User getAlexSource() {
        return User.builder()
                .username("alex@gmail.com")
                .password(encryptedPassword)
                .fullname("Alex")
                .role(Role.USER)
                .expired(false)
                .locked(false)
                .credentialsExpired(false)
                .enabled(false)
                .emailValidationKey(alexEmailValidationKey)
                .validEmailEndDate(new Date(200,06,29))
                .build();
    }

    public static User getAlexSave() {
        User user = getAlexSource();
        user.setId(2);
        return user;
    }

    // -----------------------------------------------------------------------------------------------
    //
    // -----------------------------------------------------------------------------------------------
    public static User getLoginSource() {
        return User.builder()
                .username("user@gmail.com")
                .password(encryptedPassword)
                .fullname("User")
                .role(Role.USER)
                .build();
    }

    public static User getLoginSave() {
        User user = getUserSource();
        user.setId(1);
        return user;
    }

    // -----------------------------------------------------------------------------------------------
    //
    // -----------------------------------------------------------------------------------------------
    public static Register getRegisterSource() {
        return Register.builder()
                .username("user@gmail.com")
                .password(unencryptedPassword)
                .fullname("User")
                .role(Role.USER)
                .build();
    }

    public static Register getRegisterSave() {
        Register register = getRegisterSource();
        register.setId(1);
        return register;
    }

    // -----------------------------------------------------------------------------------------------
    //
    // -----------------------------------------------------------------------------------------------
    public static Register getAlexRegisterSave() {
        return Register.builder()
                .id(2)
                .username("alex@gmail.com")
                .password(encryptedPassword)
                .fullname("Alex")
                .role(Role.USER)
                .build();
    }

    public static Register getAdminegisterSave() {
        return Register.builder()
                .id(3)
                .username("admin@gmail.com")
                .password(encryptedPassword)
                .fullname("Administrator")
                .role(Role.ADMIN)
                .build();
    }

    // -----------------------------------------------------------------------------------------------
    //
    // -----------------------------------------------------------------------------------------------
    public static MultiValueMap<String, String> getRegisterSourceController() {
        return MultiValueMapMapper.convert(getRegisterSource());
    }

    public static MultiValueMap<String, String> getRegisterSaveController() {
        return MultiValueMapMapper.convert(getRegisterSave());
    }

    // -----------------------------------------------------------------------------------------------
    //
    // -----------------------------------------------------------------------------------------------
    public final static String scriptCreateUser = "/data/createUser.sql";
}

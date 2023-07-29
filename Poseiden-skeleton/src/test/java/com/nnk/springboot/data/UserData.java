package com.nnk.springboot.data;

import com.nnk.springboot.domain.Register;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.mapper.MultiValueMapMapper;
import org.springframework.util.MultiValueMap;

import java.sql.Date;

/**
 * UserData is the class containing the user test data
 *
 * @author MC
 * @version 1.0
 */
public class UserData {

    public static User getUserSource() {
        return User.builder()
                .username("user@gmail.com")
                .password("$2a$10$55oEaTtOLOQZqJcu/ytTlO7bzdoo2tbRKNUsrJpU4W1wfLKw/opD.")
                .fullname("User")
                .role("USER")
                .expired(false)
                .locked(false)
                .credentialsExpired(false)
                .enabled(true)
                .emailValidationKey("cf0551e9-1c63-4b93-ae6e-bb3966c83e83")
                .validEmailEndDate(new Date(123,06,29))
                .build();
    }

    public static User getUserSave() {
        User user = getUserSource();
        user.setId(1);
        return user;
    }

    public static User getAlexSource() {
        return User.builder()
                .username("alex@gmail.com")
                .password("$2a$10$55oEaTtOLOQZqJcu/ytTlO7bzdoo2tbRKNUsrJpU4W1wfLKw/opD.")
                .fullname("Alex")
                .role("USER")
                .expired(false)
                .locked(false)
                .credentialsExpired(false)
                .enabled(false)
                .emailValidationKey("cf0551e9-1c63-4b93-ae6e-bb3966c83e85")
                .validEmailEndDate(new Date(200,06,29))
                .build();
    }

    public static User getAlexSave() {
        User user = getAlexSource();
        user.setId(1);
        return user;
    }

    public static Register getRegisterSource() {
        return Register.builder()
                .email("user@gmail.com")
                .password("$2a$10$55oEaTtOLOQZqJcu/ytTlO7bzdoo2tbRKNUsrJpU4W1wfLKw/opD.")
                .firstName("user first")
                .lastName("user last")
                .build();
    }

    public static MultiValueMap<String, String> getRegisterController() {
        return MultiValueMapMapper.convert(getRegisterSource());
    }

    public final static String scriptCreateUser = "/data/createUser.sql";
}

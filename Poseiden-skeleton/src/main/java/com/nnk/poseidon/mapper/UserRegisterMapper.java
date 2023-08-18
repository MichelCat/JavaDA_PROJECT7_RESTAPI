package com.nnk.poseidon.mapper;

import com.nnk.poseidon.model.Register;
import com.nnk.poseidon.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * UserRegisterMapper is the mapper User / Register
 *
 * @author MC
 * @version 1.0
 */
//@Mapper(componentModel = "spring")
//interface  RegisterMapper {
@Component
public class UserRegisterMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // -----------------------------------------------------------------------------------------------
    // Conversion User to Register
    // -----------------------------------------------------------------------------------------------
    /**
     * Mapper User to Register
     *
     * @param user User object
     * @return Register
     */
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "username", target = "username")
//    @Mapping(source = "password", target = "password")
//    @Mapping(source = "fullname", target = "fullname")
//    @Mapping(source = "role", target = "role")
//    Register from(User user);
    public Register from(User user) {
        if (user == null) {
            return null;
        }
        Register register = Register.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .fullname(user.getFullname())
                .role(user.getRole())
                .build();
        return register;
    }

    /**
     * Conversion User list to Register list
     *
     * @param users User list
     * @return Register list
     */
    public List<Register> listFrom(List<User> users) {
        List<Register> registers = new ArrayList<>();
        users.forEach(e -> {
            registers.add(from(e));
        });
        return registers;
    }

    // -----------------------------------------------------------------------------------------------
    // Conversion User to Register
    // -----------------------------------------------------------------------------------------------
    /**
     * Mapper Register to User
     *
     * @param register Register object
     * @return User
     */
//    @Mapping(source = "username", target = "username")
//    @Mapping(source = "password", target = "password")
//    @Mapping(source = "fullname", target = "fullname")
//    @Mapping(source = "role", target = "role")
//    Register from(User user);
    public User addUserFrom(Register register) {
        if (register == null) {
            return null;
        }
        User user = User.builder()
//                .id(user.getId())
                .username(register.getUsername())
                .password(passwordEncoder.encode(register.getPassword()))
                .fullname(register.getFullname())
                .role(register.getRole())
                .build();
        return user;
    }

    /**
     * Mapper Register and User to User
     *
     * @param register Register object
     * @param user User object
     * @return User
     */
//    @Mapping(source = "username", target = "username")
//    @Mapping(source = "password", target = "password")
//    @Mapping(source = "fullname", target = "fullname")
//    @Mapping(source = "role", target = "role")
//    @Mapping(source = "user", target = ".")
//    User updateExisting(Register register, User user);
    public User updateUserFrom(Register register
                                , User user) {
        user.setUsername(register.getUsername());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setFullname(register.getFullname());
        user.setRole(register.getRole());
        return user;
    }
}

package com.nnk.poseidon.mapper;

import com.nnk.poseidon.model.Register;
import com.nnk.poseidon.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * RegisterMapper is the mapper User / Register
 *
 * @author MC
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public abstract class RegisterMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    // -----------------------------------------------------------------------------------------------
    // Conversion User to Register
    // -----------------------------------------------------------------------------------------------
    /**
     * Mapper User to Register
     *
     * @param user User object
     * @return Register
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "fullname", target = "fullname")
    @Mapping(source = "role", target = "role")
    public abstract Register from(User user);
    /**
     * Conversion User list to Register list
     *
     * @param users User list
     * @return Register list
     */

    public abstract List<Register> listFrom(List<User> users);

    // -----------------------------------------------------------------------------------------------
    // Conversion User to Register
    // -----------------------------------------------------------------------------------------------
    /**
     * Mapper Register to User
     *
     * @param register Register object
     * @return User
     */
    @Mapping(source = "username", target = "username")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(register.getPassword()))")
    @Mapping(source = "fullname", target = "fullname")
    @Mapping(source = "role", target = "role")
    public abstract User addUserFrom(Register register);

    /**
     * Mapper Register and User to User
     *
     * @param register Register object
     * @param user User object
     * @return User
     */
    @Mapping(source = "register.username", target = "username")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(register.getPassword()))")
    @Mapping(source = "register.fullname", target = "fullname")
    @Mapping(source = "register.role", target = "role")
    public abstract User updateUserFrom(Register register, @MappingTarget User user);
}

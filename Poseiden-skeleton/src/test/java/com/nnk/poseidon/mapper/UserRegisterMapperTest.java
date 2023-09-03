package com.nnk.poseidon.mapper;

import com.nnk.poseidon.data.UserData;
import com.nnk.poseidon.model.Register;
import com.nnk.poseidon.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class UserRegisterMapperTest {

    @Autowired
    private RegisterMapper registerMapper;
    @MockBean
    private PasswordEncoder passwordEncoder;

    private User userSave;
    private List<User> userSaves;
    private User loginSource;
    private Register registerSource;
    private Register registerSave;
    private List<Register> registerSaves;
    private String encryptedPassword;


    @BeforeEach
    public void setUpBefore() {
        userSave = UserData.getUserSave();
        userSaves = new ArrayList<>();

        loginSource = UserData.getLoginSource();
        registerSource = UserData.getRegisterSource();

        registerSave = UserData.getRegisterSave();
        registerSaves = new ArrayList<>();

        encryptedPassword = loginSource.getPassword();
    }

    // -----------------------------------------------------------------------------------------------
    // Register from User
    // -----------------------------------------------------------------------------------------------
    @Test
    void from_whenUser_returnRegister() {
        // GIVEN
        registerSave.setPassword(encryptedPassword);
        // WHEN
        Register result = registerMapper.from(userSave);
        // THEN
        assertThat(result).isEqualTo(registerSave);
    }

    @Test
    void from_whenNull_returnNull() {
        // GIVEN
        // WHEN
        Register result = registerMapper.from(null);
        // THEN
        assertThat(result).isNull();
    }

    // -----------------------------------------------------------------------------------------------
    // List Register from list User
    // -----------------------------------------------------------------------------------------------
    @Test
    void listFrom_whenUserList_returnRegisterList() {
        // GIVEN
        userSaves.add(userSave);
        registerSave.setPassword(encryptedPassword);
        registerSaves.add(registerSave);
        // WHEN
        List<Register> result = registerMapper.listFrom(userSaves);
        // THEN
        assertIterableEquals(result, registerSaves);
    }

    @Test
    void listFrom_whenEmptyList_returnEmptyList() {
        // GIVEN
        // WHEN
        List<Register> result = registerMapper.listFrom(userSaves);
        // THEN
        assertIterableEquals(result, registerSaves);
    }

    // -----------------------------------------------------------------------------------------------
    // User addUserFrom Register
    // -----------------------------------------------------------------------------------------------
    @Test
    void addUserFrom_whenRegister_returnUser() {
        // GIVEN
        when(passwordEncoder.encode(any(String.class))).thenReturn(encryptedPassword);
        // WHEN
        User result = registerMapper.addUserFrom(registerSource);
        // THEN
        assertThat(result).isEqualTo(loginSource);
    }

    @Test
    void addUserFrom_whenNull_returnNull() {
        // GIVEN
        // WHEN
        User result = registerMapper.addUserFrom(null);
        // THEN
        assertThat(result).isNull();
    }

    // -----------------------------------------------------------------------------------------------
    // User updateUserFrom Register
    // -----------------------------------------------------------------------------------------------
    @Test
    void updateUserFrom_whenRegister_returnUser() {
        // GIVEN
        when(passwordEncoder.encode(any(String.class))).thenReturn(encryptedPassword);
        // WHEN
        User result = registerMapper.updateUserFrom(registerSave, userSave);
        // THEN
        assertThat(result).isEqualTo(userSave);
        assertSame(result, userSave);
    }
}

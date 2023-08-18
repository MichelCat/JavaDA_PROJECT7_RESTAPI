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
public class UserRegisterMapperTest {

    @Autowired
    private UserRegisterMapper userRegisterMapper;
    @MockBean
    private PasswordEncoder passwordEncoder;

    private User userSave;
    private User loginSource;
    private Register registerSource;
    private Register registerSave;
    private User loginSave;
    private List<Register> registerSaves;
    private List<User> loginSaves;
    private String unencryptedPassword;
    private String encryptedPassword;


    @BeforeEach
    public void setUpBefore() {
        userSave = UserData.getUserSave();

        loginSource = UserData.getLoginSource();
        registerSource = UserData.getRegisterSource();

        registerSave = UserData.getRegisterSave();
        registerSaves = new ArrayList<>();

        loginSave = UserData.getLoginSave();
        loginSaves = new ArrayList<>();

        unencryptedPassword = registerSource.getPassword();
        encryptedPassword = loginSource.getPassword();
    }

    // -----------------------------------------------------------------------------------------------
    // Register from User
    // -----------------------------------------------------------------------------------------------
    @Test
    public void from_whenUser_returnRegister() {
        // GIVEN
        registerSave.setPassword(encryptedPassword);
        // WHEN
        Register result = userRegisterMapper.from(loginSave);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(registerSave);
    }

    @Test
    public void from_whenNull_returnNull() {
        // GIVEN
        // WHEN
        Register result = userRegisterMapper.from(null);
        // THEN
        assertThat(result).isNull();
    }

    // -----------------------------------------------------------------------------------------------
    // List Register from list User
    // -----------------------------------------------------------------------------------------------
    @Test
    public void listFrom_whenUserList_returnRegisterList() {
        // GIVEN
        loginSaves.add(loginSave);
        registerSave.setPassword(encryptedPassword);
        registerSaves.add(registerSave);
        // WHEN
        List<Register> result = userRegisterMapper.listFrom(loginSaves);
        // THEN
        assertIterableEquals(result, registerSaves);
    }

    @Test
    public void listFrom_whenEmptyList_returnEmptyList() {
        // GIVEN
        // WHEN
        List<Register> result = userRegisterMapper.listFrom(loginSaves);
        // THEN
        assertIterableEquals(result, registerSaves);
    }

    // -----------------------------------------------------------------------------------------------
    // User addUserFrom Register
    // -----------------------------------------------------------------------------------------------
    @Test
    public void addUserFrom_whenRegister_returnUser() {
        // GIVEN
        when(passwordEncoder.encode(any(String.class))).thenReturn(encryptedPassword);
        // WHEN
        User result = userRegisterMapper.addUserFrom(registerSource);
        // THEN
        assertThat(result).usingRecursiveComparison()
//                .ignoringFields("id")
                .isEqualTo(loginSource);
    }

    @Test
    public void addUserFrom_whenNull_returnNull() {
        // GIVEN
        // WHEN
        User result = userRegisterMapper.addUserFrom(null);
        // THEN
        assertThat(result).isNull();
    }

    // -----------------------------------------------------------------------------------------------
    // User updateUserFrom Register
    // -----------------------------------------------------------------------------------------------
    @Test
    public void updateUserFrom_whenRegister_returnUser() {
        // GIVEN
        when(passwordEncoder.encode(any(String.class))).thenReturn(encryptedPassword);
        // WHEN
        User result = userRegisterMapper.updateUserFrom(registerSource, userSave);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(userSave);
        assertSame(result, userSave);
    }
}

package com.nnk.poseidon.business;

import com.nnk.poseidon.data.UserData;
import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.mapper.RegisterMapper;
import com.nnk.poseidon.model.Register;
import com.nnk.poseidon.model.User;
import com.nnk.poseidon.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * UserBusinessTest is a unit testing class of the Users service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class UserBusinessTest {

    @Autowired
    private UserBusiness userBusiness;
    @Autowired
    private MessageSource messageSource;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RegisterMapper registerMapper;

    private TestMessageSource testMessageSource;
    private User loginSource;
    private Register registerSource;
    private User userSave;
    public List<User> userSaves;
    private Register registerSave;
    private List<Register> registerSaves;
    private String encryptedPassword;


    @BeforeEach
    public void setUpBefore() {
        testMessageSource = new TestMessageSource(messageSource);

        loginSource = UserData.getLoginSource();
        registerSource = UserData.getRegisterSource();

        userSave = UserData.getUserSave();
        userSaves = new ArrayList<>();

        registerSave = UserData.getRegisterSave();
        registerSaves = new ArrayList<>();

        encryptedPassword = userSave.getPassword();
    }

    // -----------------------------------------------------------------------------------------------
    // getUsersList method
    // -----------------------------------------------------------------------------------------------
    @Test
    void getUsersList_findAllNormal() {
        // GIVEN
        userSaves.add(userSave);
        when(userRepository.findAll()).thenReturn(userSaves);
        registerSave.setPassword(encryptedPassword);
        registerSaves.add(registerSave);
        when(registerMapper.listFrom(userSaves)).thenReturn(registerSaves);
        // WHEN
        assertThat(userBusiness.getUsersList()).isEqualTo(registerSaves);
        // THEN
        verify(registerMapper, Mockito.times(1)).listFrom(userSaves);
    }

    @Test
    void getUsersList_findAllEmpty() {
        // GIVEN
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        when(registerMapper.listFrom(any(List.class))).thenReturn(new ArrayList<>());
        // WHEN
        assertThat(userBusiness.getUsersList()).isEmpty();
        // THEN
        verify(registerMapper, Mockito.times(1)).listFrom(any(List.class));
    }

    // -----------------------------------------------------------------------------------------------
    // createUser method
    // -----------------------------------------------------------------------------------------------
    @Test
    void createUser_saveNormal() throws MyException {
        // GIVEN
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(registerMapper.addUserFrom(any(Register.class))).thenReturn(loginSource);
        when(userRepository.save(any(User.class))).thenReturn(userSave);
        // WHEN
        assertThat(userBusiness.createUser(registerSource)).isEqualTo(userSave);
        // THEN
        verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    void createUser_nullRegisterParameter_returnNullPointer() {
        // GIVEN
        when(userRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> userBusiness.createUser(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.user.nullUser", new Object[] { null });
        verify(userRepository, Mockito.times(0)).save(any(User.class));
    }

    @Test
    void createUser_UserExist_returnMyException() {
        // GIVEN
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(userSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> userBusiness.createUser(registerSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.user.userExists", new Object[] { registerSave.getId() });
        verify(userRepository, Mockito.times(0)).save(any(User.class));
    }

    // -----------------------------------------------------------------------------------------------
    // getRegisterById method
    // -----------------------------------------------------------------------------------------------
    @Test
    void getRegisterById_findByIdNormal() throws MyException {
        // GIVEN
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(userSave));
        when(registerMapper.from(any(User.class))).thenReturn(registerSave);
        // WHEN
        assertThat(userBusiness.getRegisterById(1)).isEqualTo(registerSave);
        // THEN
        verify(userRepository, Mockito.times(1)).findById(any(Integer.class));
    }

    @Test
    void getRegisterById_nullIdParameter_returnMyException() {
        // GIVEN
        when(userRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> userBusiness.getRegisterById(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.user.unknown", new Object[] { null });
        verify(userRepository, Mockito.times(1)).findById(null);
    }

    // -----------------------------------------------------------------------------------------------
    // updateUser method
    // -----------------------------------------------------------------------------------------------
    @Test
    void updateUser_updateNormal() throws MyException {
        // GIVEN
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(userSave));
        when(registerMapper.updateUserFrom(registerSave, userSave)).thenReturn(userSave);
        when(userRepository.save(userSave)).thenReturn(userSave);
        // WHEN
        assertThat(userBusiness.updateUser(1, registerSave)).isEqualTo(userSave);
        // THEN
        verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    void updateUser_UserNotExist_returnMyException() {
        // GIVEN
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> userBusiness.updateUser(2, registerSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.user.unknown", new Object[] { 2 });
        verify(userRepository, Mockito.times(0)).save(any(User.class));
    }

    @Test
    void updateUser_nullIdParameter_returnMyException() {
        // GIVEN
        when(userRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> userBusiness.updateUser(null, registerSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.user.unknown", new Object[] { null });
        verify(userRepository, Mockito.times(0)).save(any(User.class));
    }

    @Test
    void updateUser_zeroIdParameter_returnMyException() {
        // GIVEN
        when(userRepository.findById(0)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> userBusiness.updateUser(0, registerSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.user.unknown", new Object[] { 0 });
        verify(userRepository, Mockito.times(0)).save(any(User.class));
    }

    @Test
    void updateUser_nullRegisterParameter_returnMyException() {
        // GIVEN
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(userSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> userBusiness.updateUser(1, null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.user.nullUser", null);
        verify(userRepository, Mockito.times(0)).save(any(User.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteUser method
    // -----------------------------------------------------------------------------------------------
    @Test
    void deleteUser_deleteNormal() throws MyException {
        // GIVEN
        when(userRepository.existsById(any(Integer.class))).thenReturn(true);
        doNothing().when(userRepository).deleteById(any(Integer.class));
        // WHEN
        userBusiness.deleteUser(1);
        // THEN
        verify(userRepository, Mockito.times(1)).deleteById(any(Integer.class));
    }

    @Test
    void deleteUser_UserNotExist_returnMyException() {
        // GIVEN
        when(userRepository.existsById(any(Integer.class))).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> userBusiness.deleteUser(2));
        // THEN
        testMessageSource.compare(exception
                            , "exception.user.unknown", new Object[] { 2 });
        verify(userRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    void deleteUser_nullIdParameter_returnMyException() {
        // GIVEN
        when(userRepository.existsById(null)).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> userBusiness.deleteUser(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.user.unknown", new Object[] { null });
        verify(userRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    void deleteUser_zeroIdParameter_returnMyException() {
        // GIVEN
        when(userRepository.existsById(0)).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> userBusiness.deleteUser(0));
        // THEN
        testMessageSource.compare(exception
                            , "exception.user.unknown", new Object[] { 0 });
        verify(userRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }
}

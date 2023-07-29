package com.nnk.springboot.business;

import com.nnk.springboot.Exception.MessagePropertieFormat;
import com.nnk.springboot.Exception.MyException;
import com.nnk.springboot.data.UserData;
import com.nnk.springboot.domain.Register;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.utils.EmailBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSendException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


/**
 * LoginBusinessTest is a class of unit tests on login.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class LoginBusinessTest {

    @Autowired
    private LoginBusiness loginBusiness;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private EmailBusiness emailBusiness;

    private User userSource;
    private User userSave;
    private Register registerSource;
    private String userEmail;


    @BeforeEach
    public void setUpBefore() {
        userSource = UserData.getUserSource();
        userSave = UserData.getUserSave();
        registerSource = UserData.getRegisterSource();
        userEmail = userSave.getUsername();
    }

    // -----------------------------------------------------------------------------------------------
    // LoadUserByUsername method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void loadUserByUsername_userNotExist_returnNotFound() {
        // GIVEN
        when(userRepository.findByUsername(userEmail)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(UsernameNotFoundException.class,
                () -> {loginBusiness.loadUserByUsername(userEmail);});
        // THEN
        String messageError = MessagePropertieFormat.getMessage("throw.UserNotFound", userEmail);
        assertThat(exception.getMessage()).isEqualTo(messageError);
    }

    @Test
    public void loadUserByUsername_enabledFalse_returnNotFound() {
        // GIVEN
        userSave.setEnabled(false);
        when(userRepository.findByUsername(userEmail)).thenReturn(Optional.of(userSave));
        // WHEN
        Throwable exception = assertThrows(UsernameNotFoundException.class,
                () -> {loginBusiness.loadUserByUsername(userEmail);});
        // THEN
        String messageError = MessagePropertieFormat.getMessage("throw.AccountNotActivated", userEmail);
        assertThat(exception.getMessage()).isEqualTo(messageError);
    }

    @Test
    public void loadUserByUsername_expiredTrue_returnNotFound() {
        // GIVEN
        userSave.setExpired(true);
        when(userRepository.findByUsername(userEmail)).thenReturn(Optional.of(userSave));
        // WHEN
        Throwable exception = assertThrows(UsernameNotFoundException.class,
                () -> {loginBusiness.loadUserByUsername(userEmail);});
        // THEN
        String messageError = MessagePropertieFormat.getMessage("throw.AccountExpired", userEmail);
        assertThat(exception.getMessage()).isEqualTo(messageError);
    }

    @Test
    public void loadUserByUsername_lockedTrue_returnNotFound() {
        // GIVEN
        userSave.setLocked(true);
        when(userRepository.findByUsername(userEmail)).thenReturn(Optional.of(userSave));
        // WHEN
        Throwable exception = assertThrows(UsernameNotFoundException.class,
                () -> {loginBusiness.loadUserByUsername(userEmail);});
        // THEN
        String messageError = MessagePropertieFormat.getMessage("throw.AccountLocked", userEmail);
        assertThat(exception.getMessage()).isEqualTo(messageError);
    }

    @Test
    public void loadUserByUsername_credentialsExpiredTrue_returnNotFound() {
        // GIVEN
        userSave.setCredentialsExpired(true);
        when(userRepository.findByUsername(userEmail)).thenReturn(Optional.of(userSave));
        // WHEN
        Throwable exception = assertThrows(UsernameNotFoundException.class,
                () -> {loginBusiness.loadUserByUsername(userEmail);});
        // THEN
        String messageError = MessagePropertieFormat.getMessage("throw.PasswordExpired", userEmail);
        assertThat(exception.getMessage()).isEqualTo(messageError);
    }

    // -----------------------------------------------------------------------------------------------
    // AddUser method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void addUser_normal() throws Exception {
        // GIVEN
        when(userRepository.findByUsername(userEmail)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userSave);
        doNothing().when(emailBusiness).sendEmail(any(String.class), any(String.class), any(String.class), any(String.class));
        // WHEN
        loginBusiness.addUser(registerSource);
        // THEN
        verify(emailBusiness, Mockito.times(1))
                .sendEmail(any(String.class), any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void addUser_userExist_returnMyException() {
        // GIVEN
        when(userRepository.findByUsername(userEmail)).thenReturn(Optional.of(userSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class,
                () -> {loginBusiness.addUser(registerSource);});
        // THEN
        String messageError = MessagePropertieFormat.getMessage("throw.EmailAccountAlreadyeExists", userEmail);
        assertThat(exception.getMessage()).isEqualTo(messageError);
    }

    @Test
    public void addUser_sendError_returnMailSendException() throws Exception {
        // GIVEN
        when(userRepository.findByUsername(userEmail)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userSave);
        doThrow(new MailSendException("Test message"))
            .when(emailBusiness).sendEmail(any(String.class), any(String.class)
                , any(String.class), any(String.class));
        // WHEN
        assertThrows(MyException.class,
                () -> {loginBusiness.addUser(registerSource);});
        // THEN
        verify(emailBusiness, Mockito.times(1))
                .sendEmail(any(String.class), any(String.class), any(String.class), any(String.class));
    }
}

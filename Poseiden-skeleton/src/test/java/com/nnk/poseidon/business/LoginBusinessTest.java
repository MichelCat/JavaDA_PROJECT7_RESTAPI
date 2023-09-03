package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.data.UserData;
import com.nnk.poseidon.mapper.RegisterMapper;
import com.nnk.poseidon.model.Register;
import com.nnk.poseidon.model.User;
import com.nnk.poseidon.repository.UserRepository;
import com.nnk.poseidon.utils.EmailBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
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
class LoginBusinessTest {

    @Autowired
    private LoginBusiness loginBusiness;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private EmailBusiness emailBusiness;
    @MockBean
    private RegisterMapper registerMapper;
    @Autowired
    private MessageSource messageSource;

    private TestMessageSource testMessageSource;
    private User userSave;
    private User loginSource;
    private Register registerSource;
    private String userEmail;


    @BeforeEach
    public void setUpBefore() {
        testMessageSource = new TestMessageSource(messageSource);

        userSave = UserData.getUserSave();

        loginSource = UserData.getLoginSource();

        registerSource = UserData.getRegisterSource();

        userEmail = userSave.getUsername();
    }

    // -----------------------------------------------------------------------------------------------
    // loadUserByUsername method
    // -----------------------------------------------------------------------------------------------
    @Test
    void loadUserByUsername_userNotExist_returnMyException() {
        // GIVEN
        when(userRepository.findByUsername(userEmail)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(UsernameNotFoundException.class,
                () -> {loginBusiness.loadUserByUsername(userEmail);});
        // THEN
        testMessageSource.compare(exception
                            , "exception.UserNotFound", new Object[] { userEmail });
    }

    @Test
    void loadUserByUsername_enabledFalse_returnMyException() {
        // GIVEN
        userSave.setEnabled(false);
        when(userRepository.findByUsername(userEmail)).thenReturn(Optional.of(userSave));
        // WHEN
        Throwable exception = assertThrows(UsernameNotFoundException.class,
                () -> {loginBusiness.loadUserByUsername(userEmail);});
        // THEN
        testMessageSource.compare(exception
                            , "exception.AccountNotActivated", new Object[] { userEmail });
    }

    @Test
    void loadUserByUsername_expiredTrue_returnMyException() {
        // GIVEN
        userSave.setExpired(true);
        when(userRepository.findByUsername(userEmail)).thenReturn(Optional.of(userSave));
        // WHEN
        Throwable exception = assertThrows(UsernameNotFoundException.class,
                () -> {loginBusiness.loadUserByUsername(userEmail);});
        // THEN
        testMessageSource.compare(exception
                            , "exception.AccountExpired", new Object[] { userEmail });
    }

    @Test
    void loadUserByUsername_lockedTrue_returnMyException() {
        // GIVEN
        userSave.setLocked(true);
        when(userRepository.findByUsername(userEmail)).thenReturn(Optional.of(userSave));
        // WHEN
        Throwable exception = assertThrows(UsernameNotFoundException.class,
                () -> {loginBusiness.loadUserByUsername(userEmail);});
        // THEN
        testMessageSource.compare(exception
                            , "exception.AccountLocked", new Object[] { userEmail });
    }

    @Test
    void loadUserByUsername_credentialsExpiredTrue_returnMyException() {
        // GIVEN
        userSave.setCredentialsExpired(true);
        when(userRepository.findByUsername(userEmail)).thenReturn(Optional.of(userSave));
        // WHEN
        Throwable exception = assertThrows(UsernameNotFoundException.class,
                () -> {loginBusiness.loadUserByUsername(userEmail);});
        // THEN
        testMessageSource.compare(exception
                            , "exception.PasswordExpired", new Object[] { userEmail });
    }

    // -----------------------------------------------------------------------------------------------
    // addUser method
    // -----------------------------------------------------------------------------------------------
    @Test
    void addUser_normal() throws Exception {
        // GIVEN
        when(userRepository.existsByUsername(userEmail)).thenReturn(false);
        when(registerMapper.addUserFrom(any(Register.class))).thenReturn(loginSource);
        when(userRepository.save(any(User.class))).thenReturn(userSave);
        doNothing().when(emailBusiness).sendEmail(any(String.class), any(String.class), any(String.class), any(String.class));
        // WHEN
        loginBusiness.addUser(registerSource);
        // THEN
        verify(emailBusiness, Mockito.times(1))
                .sendEmail(any(String.class), any(String.class), any(String.class), any(String.class));
    }

    @Test
    void addUser_userExist_returnMyException() {
        // GIVEN
        when(userRepository.existsByUsername(userEmail)).thenReturn(true);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> {loginBusiness.addUser(registerSource);});
        // THEN
        testMessageSource.compare(exception
                            , "exception.EmailAccountAlreadyExists", new Object[] { userEmail });
        verify(emailBusiness, Mockito.times(0))
                .sendEmail(any(String.class), any(String.class), any(String.class), any(String.class));
    }

    @Test
    void addUser_sendErrorMailSendException_returnMyException() throws Exception {
        // GIVEN
        when(userRepository.existsByUsername(userEmail)).thenReturn(false);
        when(registerMapper.addUserFrom(any(Register.class))).thenReturn(loginSource);
        when(userRepository.save(any(User.class))).thenReturn(userSave);
        doThrow(new MailSendException("Test message"))
            .when(emailBusiness).sendEmail(any(String.class), any(String.class)
                , any(String.class), any(String.class));
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> {loginBusiness.addUser(registerSource);});
        // THEN
        testMessageSource.compare(exception
                            , "exception.ErrorSendEmail", new Object[] { userEmail });
        verify(emailBusiness, Mockito.times(1))
                .sendEmail(any(String.class), any(String.class), any(String.class), any(String.class));
    }
}

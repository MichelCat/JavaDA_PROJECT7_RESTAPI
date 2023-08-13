package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MessagePropertieFormat;
import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.UserData;
import com.nnk.poseidon.model.User;
import com.nnk.poseidon.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * EmailActivationBusinessTest is a class of unit tests on email.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmailActivationBusinessTest {

    @Autowired
    private EmailActivationBusiness emailActivationBusiness;

    @MockBean
    private UserRepository userRepository;

    private User userSave;
    private String keyValue;

    @BeforeEach
    public void setUpBefore() {
        userSave = UserData.getUserSave();
        userSave.createValidationEmail();
        keyValue = userSave.getEmailValidationKey();
    }

    // -----------------------------------------------------------------------------------------------
    // activatedUser method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void activatedUser_normal_returnUser() throws MyException {
        // GIVEN
        userSave.setEnabled(false);
        when(userRepository.findByEmailValidationKey(keyValue)).thenReturn(Optional.of(userSave));
        when(userRepository.save(userSave)).thenReturn(userSave);
        // WHEN
        assertThat(emailActivationBusiness.activatedUser(keyValue)).isEqualTo(userSave);
        // THEN
    }

    @Test
    public void activatedUser_userNotExist_returnMyException() {
        // GIVEN
        when(userRepository.findByEmailValidationKey(keyValue)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class,
                () -> {emailActivationBusiness.activatedUser(keyValue);});
        // THEN
        String messageError = MessagePropertieFormat.getMessage("throw.CustomerNotExist", "");
        assertThat(exception.getMessage()).isEqualTo(messageError);
    }

    @Test
    public void activatedUser_validEmailKeyError_returnMyException() {
        // GIVEN
        userSave.setEmailValidationKey("KeyError");
        when(userRepository.findByEmailValidationKey(keyValue)).thenReturn(Optional.of(userSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class,
                () -> {emailActivationBusiness.activatedUser(keyValue);});
        // THEN
        String messageError = MessagePropertieFormat.getMessage("throw.InvalidEmailKey");
        assertThat(exception.getMessage()).isEqualTo(messageError);
    }

    @Test
    public void activatedUser_validEmailEndDateError_returnMyException() {
        // GIVEN
        userSave.setValidEmailEndDate(GlobalData.CURRENT_TIMESTAMP);
        when(userRepository.findByEmailValidationKey(keyValue)).thenReturn(Optional.of(userSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class,
                () -> {emailActivationBusiness.activatedUser(keyValue);});
        // THEN
        String messageError = MessagePropertieFormat.getMessage("throw.EmailTimeExceeded");
        assertThat(exception.getMessage()).isEqualTo(messageError);
    }

    @Test
    public void activatedUser_validedEmail_returnMyException() {
        // GIVEN
        when(userRepository.findByEmailValidationKey(keyValue)).thenReturn(Optional.of(userSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class,
                () -> {emailActivationBusiness.activatedUser(keyValue);});
        // THEN
        String messageError = MessagePropertieFormat.getMessage("throw.AccountAlreadyActivated");
        assertThat(exception.getMessage()).isEqualTo(messageError);
    }
}

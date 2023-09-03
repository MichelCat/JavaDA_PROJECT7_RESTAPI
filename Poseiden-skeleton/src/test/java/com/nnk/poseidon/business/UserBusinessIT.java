package com.nnk.poseidon.business;

import com.nnk.poseidon.data.UserData;
import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.model.Register;
import com.nnk.poseidon.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * UserBusinessIT is a class of integration tests on Users service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserBusinessIT {

    @Autowired
    private UserBusiness userBusiness;
    @Autowired
    private MessageSource messageSource;

    private TestMessageSource testMessageSource;
    private User userSave;
    private Register registerSource;
    private Register registerSave;
    private Register alexRegisterSave;
    private Register adminRegisterSave;
    private String encryptedPassword;
    private Integer userId;


    @BeforeEach
    public void setUpBefore() {
        testMessageSource = new TestMessageSource(messageSource);

        userSave = UserData.getUserSave();

        registerSource = UserData.getRegisterSource();

        registerSave = UserData.getRegisterSave();
        alexRegisterSave = UserData.getAlexRegisterSave();
        adminRegisterSave = UserData.getAdminegisterSave();

        encryptedPassword = userSave.getPassword();

        userId = userSave.getId();
    }

    // -----------------------------------------------------------------------------------------------
    // getUsersList method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    void getUsersList_findAllNormal() {
        // GIVEN
        registerSave.setPassword(encryptedPassword);
        alexRegisterSave.setPassword(encryptedPassword);
        adminRegisterSave.setPassword(encryptedPassword);
        // WHEN
        List<Register> result = userBusiness.getUsersList();
        // THEN
        assertThat(result).hasSize(3)
                            .contains(registerSave)
                            .contains(alexRegisterSave)
                            .contains(adminRegisterSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void getUsersList_findAllEmpty() {
        // GIVEN
        // WHEN
        List<Register> result = userBusiness.getUsersList();
        // THEN
        assertThat(result).isEmpty();
    }

    // -----------------------------------------------------------------------------------------------
    // createUser method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void createUser_userNotExist() throws MyException {
        // GIVEN
        // WHEN
        User result = userBusiness.createUser(registerSource);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("password")
                .ignoringFields("emailValidationKey")
                .ignoringFields("validEmailEndDate")
                .isEqualTo(userSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    void createUser_userExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> userBusiness.createUser(registerSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.user.userExists", new Object[] { registerSave.getId() });
    }

    // -----------------------------------------------------------------------------------------------
    // getUserById method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    void getUserById_userExist() throws MyException {
        // GIVEN
        // WHEN
        Register result = userBusiness.getRegisterById(userId);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(registerSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void getUserById_userNotExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> userBusiness.getRegisterById(userId));
        // THEN
        testMessageSource.compare(exception
                            , "exception.user.unknown", new Object[] { userId });
    }

    // -----------------------------------------------------------------------------------------------
    // updateUser method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    void updateUser_userExist() throws MyException {
        // GIVEN
        // WHEN
        User result = userBusiness.updateUser(userId, registerSave);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("password")
                .ignoringFields("validEmailEndDate")
                .isEqualTo(userSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void updateUser_userNotExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> userBusiness.updateUser(userId, registerSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.user.unknown", new Object[] { userId });
    }

    // -----------------------------------------------------------------------------------------------
    // deleteUser method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = UserData.scriptCreateUser)
    void deleteUser_userExist() throws MyException {
        // GIVEN
        // WHEN
        userBusiness.deleteUser(userId);
        // THEN
        Throwable exception =assertThrows(MyException.class, () -> userBusiness.getRegisterById(userId));
        testMessageSource.compare(exception
                            , "exception.user.unknown", new Object[] { userId });
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void deleteUser_userNotExist() {
        // GIVEN
        // WHEN
        Throwable exception =assertThrows(MyException.class, () -> userBusiness.deleteUser(userId));
        // THEN
        testMessageSource.compare(exception
                            , "exception.user.unknown", new Object[] { userId });
    }
}

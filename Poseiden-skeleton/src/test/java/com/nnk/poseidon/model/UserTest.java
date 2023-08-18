package com.nnk.poseidon.model;

import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.UserData;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * UserTest is the unit test class managing the User
 *
 * @author MC
 * @version 1.0
 */
//@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserTest {

    @Autowired
    private Validator validator;

    private TestConstraintViolation<User> testConstraintViolation;

    private User user;
    private Timestamp currentTimestamp;

    @BeforeEach
    public void setUpBefore() {
        currentTimestamp = GlobalData.CURRENT_TIMESTAMP;

        testConstraintViolation = new TestConstraintViolation<>(validator);

        user = UserData.getUserSource();
    }

    // -----------------------------------------------------------------------------------------------
    // id attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void id_TestBuildAndNew_thenEqual() {
        // GIVEN
        // WHEN
        User objBuild = User.builder()
                .build();
        User objNew = new User();
        // THEN
        assertThat(objBuild).usingRecursiveComparison().isEqualTo(objNew);
    }

    // -----------------------------------------------------------------------------------------------
    // username attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void username_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        user.setUsername("alex@gmail.com");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getUsername()).isEqualTo("alex@gmail.com");
    }

    @Test
    public void username_blank_thenTwoConstraintViolation() {
        // GIVEN
        // WHEN
        user.setUsername(" ");
        // THEN
        String[][] errorList = {{"username", "Username is mandatory"}
                                , {"username", "Email should be valid"}};
        testConstraintViolation.checking(user, errorList);
    }

    @Test
    public void username_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setUsername("");
        // THEN
        String[][] errorList = {{"username", "Username is mandatory"}};
        testConstraintViolation.checking(user, errorList);
    }

    @Test
    public void username_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setUsername(null);
        // THEN
        String[][] errorList = {{"username", "Username is mandatory"}};
        testConstraintViolation.checking(user, errorList);
    }

    @Test
    public void username_sizeTooBig_thenTwoConstraintViolation() {
        // GIVEN
        // WHEN
        user.setUsername(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"username", "Maximum length of 125 characters"}
                                , {"username", "Email should be valid"}};
        testConstraintViolation.checking(user, errorList);
    }

    @Test
    public void username_emailError_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setUsername("emailError");
        // THEN
        String[][] errorList = {{"username", "Email should be valid"}};
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // password attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void password_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        user.setPassword("PasswordTest");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getPassword()).isEqualTo("PasswordTest");
    }

    @Test
    public void password_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setPassword(" ");
        // THEN
        String[][] errorList = {{"password", "Password is mandatory"}};
        testConstraintViolation.checking(user, errorList);
    }

    @Test
    public void password_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setPassword("");
        // THEN
        String[][] errorList = {{"password", "Password is mandatory"}};
        testConstraintViolation.checking(user, errorList);
    }

    @Test
    public void password_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setPassword(null);
        // THEN
        String[][] errorList = {{"password", "Password is mandatory"}};
        testConstraintViolation.checking(user, errorList);
    }

    @Test
    public void password_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setPassword(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"password", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // fullname attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void fullname_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        user.setFullname("Fullname Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getFullname()).isEqualTo("Fullname Test");
    }

    @Test
    public void fullname_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setFullname(" ");
        // THEN
        String[][] errorList = {{"fullname", "FullName is mandatory"}};
        testConstraintViolation.checking(user, errorList);
    }

    @Test
    public void fullname_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setFullname("");
        // THEN
        String[][] errorList = {{"fullname", "FullName is mandatory"}};
        testConstraintViolation.checking(user, errorList);
    }

    @Test
    public void fullname_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setFullname(null);
        // THEN
        String[][] errorList = {{"fullname", "FullName is mandatory"}};
        testConstraintViolation.checking(user, errorList);
    }

    @Test
    public void fullname_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setFullname(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"fullname", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // role attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void role_whenURoleUser_thenRoleUser() {
        // GIVEN
        // WHEN
        user.setRole(Role.USER);
        // THEN
        assertThat(user.getRole()).isEqualTo(Role.USER);
    }

    @Test
    public void role_whenNull_thenNull() {
        // GIVEN
        // WHEN
        user.setRole(null);
        // THEN
        assertThat(user.getRole()).isNull();
    }

    // -----------------------------------------------------------------------------------------------
    // expired attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void expired_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        user.setExpired(true);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getExpired()).isTrue();
    }

    @Test
    public void expired_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setExpired(null);
        // THEN
        String[][] errorList = {{"expired", "User account expired must not be null"}};
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // locked attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void locked_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        user.setLocked(true);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getLocked()).isTrue();
    }

    @Test
    public void locked_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setLocked(null);
        // THEN
        String[][] errorList = {{"locked", "User locked must not be null"}};
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // credentialsExpired attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void credentialsExpired_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        user.setCredentialsExpired(true);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getCredentialsExpired()).isTrue();
    }

    @Test
    public void credentialsExpired_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setCredentialsExpired(null);
        // THEN
        String[][] errorList = {{"credentialsExpired", "User credentials expired must not be null"}};
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // enabled attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void enabled_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        user.setEnabled(true);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getEnabled()).isTrue();
    }

    @Test
    public void enabled_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setEnabled(null);
        // THEN
        String[][] errorList = {{"enabled", "Activated user must not be null"}};
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // emailValidationKey attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void emailValidationKey_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        user.setEmailValidationKey("EmailValidationKey Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getEmailValidationKey()).isEqualTo("EmailValidationKey Test");
    }

    @Test
    public void emailValidationKey_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setEmailValidationKey(StringUtils.repeat('a', 37));
        // THEN
        String[][] errorList = {{"emailValidationKey", "Maximum length of 36 characters"}};
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // validEmailEndDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void validEmailEndDate_normal() {
        // GIVEN
        // WHEN
        user.setValidEmailEndDate(currentTimestamp);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getValidEmailEndDate()).isEqualTo(currentTimestamp);
    }
}

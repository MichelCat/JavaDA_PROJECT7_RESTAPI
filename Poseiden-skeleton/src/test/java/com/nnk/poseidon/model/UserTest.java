package com.nnk.poseidon.model;

import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.UserData;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.stream.Stream;

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
class UserTest {

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
    // builder method
    // -----------------------------------------------------------------------------------------------
    @Test
    void builder_TestBuildAndNew_thenEqual() {
        // GIVEN
        // WHEN
        User objBuild = User.builder()
                .build();
        User objNew = new User();
        // THEN
        assertThat(objBuild).isEqualTo(objNew);
    }

    // -----------------------------------------------------------------------------------------------
    // username attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void username_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        user.setUsername("alex@gmail.com");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getUsername()).isEqualTo("alex@gmail.com");
    }

    private static Stream<Arguments> listOfUsernameToTest() {
        String[][] errorSpace = {{"username", "{constraint.notBlank.user.username}"} , {"username", "{constraint.email.user.username}"}};
        String[][] errorEmpty = {{"username", "{constraint.notBlank.user.username}"}};
        String[][] errorNull = {{"username", "{constraint.notBlank.user.username}"}};
        String[][] errorSizeTooBig = {{"username", "{constraint.size.global}"}, {"username", "{constraint.email.user.username}"}};
        String[][] errorEmailError = {{"username", "{constraint.email.user.username}"}};

        return Stream.of(
                Arguments.of(" ", errorSpace, "space")
                , Arguments.of("", errorEmpty, "empty")
                , Arguments.of(null, errorNull, "null")
                , Arguments.of(StringUtils.repeat('a', 126), errorSizeTooBig, "size too big")
                , Arguments.of("emailError", errorEmailError, "email error")
        );
    }

    @ParameterizedTest(name = "Username is {2} ({0}).")
    @MethodSource("listOfUsernameToTest")
    void username_thenConstraintViolation(String username, String[][] errorList, String message) {
        // GIVEN
        // WHEN
        user.setUsername(username);
        // THEN
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // password attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void password_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        user.setPassword("PasswordTest");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getPassword()).isEqualTo("PasswordTest");
    }

    private static Stream<Arguments> listOfPasswordToTest() {
        String[][] errorSpace = {{"password", "{constraint.notBlank.user.password}"}};
        String[][] errorEmpty = {{"password", "{constraint.notBlank.user.password}"}};
        String[][] errorNull = {{"password", "{constraint.notBlank.user.password}"}};
        String[][] errorSizeTooBig = {{"password", "{constraint.size.global}"}};

        return Stream.of(
                Arguments.of(" ", errorSpace, "space")
                , Arguments.of("", errorEmpty, "empty")
                , Arguments.of(null, errorNull, "null")
                , Arguments.of(StringUtils.repeat('a', 126), errorSizeTooBig, "size too big")
        );
    }

    @ParameterizedTest(name = "Password is {2} ({0}).")
    @MethodSource("listOfPasswordToTest")
    void password_thenConstraintViolation(String password, String[][] errorList, String message) {
        // GIVEN
        // WHEN
        user.setPassword(password);
        // THEN
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // fullname attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void fullname_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        user.setFullname("Fullname Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getFullname()).isEqualTo("Fullname Test");
    }

    private static Stream<Arguments> listOfFullnameToTest() {
        String[][] errorSpace = {{"fullname", "{constraint.notBlank.user.fullname}"}};
        String[][] errorEmpty = {{"fullname", "{constraint.notBlank.user.fullname}"}};
        String[][] errorNull = {{"fullname", "{constraint.notBlank.user.fullname}"}};
        String[][] errorSizeTooBig = {{"fullname", "{constraint.size.global}"}};

        return Stream.of(
                Arguments.of(" ", errorSpace, "space")
                , Arguments.of("", errorEmpty, "empty")
                , Arguments.of(null, errorNull, "null")
                , Arguments.of(StringUtils.repeat('a', 126), errorSizeTooBig, "size too big")
        );
    }

    @ParameterizedTest(name = "Fullname is {2} ({0}).")
    @MethodSource("listOfFullnameToTest")
    void fullname_thenConstraintViolation(String fullname, String[][] errorList, String message) {
        // GIVEN
        // WHEN
        user.setFullname(fullname);
        // THEN
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // role attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void role_whenURoleUser_thenRoleUser() {
        // GIVEN
        // WHEN
        user.setRole(Role.USER);
        // THEN
        assertThat(user.getRole()).isEqualTo(Role.USER);
    }

    @Test
    void role_whenNull_thenNull() {
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
    void expired_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        user.setExpired(true);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getExpired()).isTrue();
    }

    @Test
    void expired_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setExpired(null);
        // THEN
        String[][] errorList = {{"expired", "{constraint.notNull.user.expired}"}};
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // locked attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void locked_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        user.setLocked(true);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getLocked()).isTrue();
    }

    @Test
    void locked_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setLocked(null);
        // THEN
        String[][] errorList = {{"locked", "{constraint.notNull.user.locked}"}};
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // credentialsExpired attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void credentialsExpired_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        user.setCredentialsExpired(true);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getCredentialsExpired()).isTrue();
    }

    @Test
    void credentialsExpired_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setCredentialsExpired(null);
        // THEN
        String[][] errorList = {{"credentialsExpired", "{constraint.notNull.user.credentialsExpired}"}};
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // enabled attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void enabled_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        user.setEnabled(true);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getEnabled()).isTrue();
    }

    @Test
    void enabled_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setEnabled(null);
        // THEN
        String[][] errorList = {{"enabled", "{constraint.notNull.user.enabled}"}};
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // emailValidationKey attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void emailValidationKey_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        user.setEmailValidationKey("EmailValidationKey Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getEmailValidationKey()).isEqualTo("EmailValidationKey Test");
    }

    @Test
    void emailValidationKey_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        user.setEmailValidationKey(StringUtils.repeat('a', 37));
        // THEN
        String[][] errorList = {{"emailValidationKey", "{constraint.size.global}"}};
        testConstraintViolation.checking(user, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // validEmailEndDate attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void validEmailEndDate_normal() {
        // GIVEN
        // WHEN
        user.setValidEmailEndDate(currentTimestamp);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(user, errorList);
        assertThat(user.getValidEmailEndDate()).isEqualTo(currentTimestamp);
    }
}

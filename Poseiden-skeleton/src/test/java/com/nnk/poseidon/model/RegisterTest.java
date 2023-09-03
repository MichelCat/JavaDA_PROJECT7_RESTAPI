package com.nnk.poseidon.model;

import com.nnk.poseidon.data.UserData;
import com.nnk.poseidon.enumerator.UserRole;
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

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * RegisterTest is the unit test class managing the Register
 *
 * @author MC
 * @version 1.0
 */
@SpringBootTest
@ActiveProfiles("test")
class RegisterTest {

    @Autowired
    private Validator validator;

    private TestConstraintViolation<Register> testConstraintViolation;
    private Register registerSource;

    @BeforeEach
    public void setUpBefore() {
        testConstraintViolation = new TestConstraintViolation<>(validator);

        registerSource = UserData.getRegisterSource();
    }

    // -----------------------------------------------------------------------------------------------
    // builder method
    // -----------------------------------------------------------------------------------------------
    @Test
    void builder_TestBuildAndNew_thenEqual() {
        // GIVEN
        // WHEN
        Register objBuild = Register.builder()
                                .build();
        Register objNew = new Register();
        // THEN
        assertThat(objBuild).isEqualTo(objNew);
    }

    // -----------------------------------------------------------------------------------------------
    // password attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void password_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        registerSource.setPassword("Test123+");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(registerSource, errorList);
        assertThat(registerSource.getPassword()).isEqualTo("Test123+");
    }

    private static Stream<Arguments> listOfPasswordToTest() {
        String[][] errorInvalid = {{"password", "{constraint.passwordConstraint.global}"}};

        return Stream.of(
                Arguments.of(null, errorInvalid)            // Null
                , Arguments.of("", errorInvalid)            // Empty
                , Arguments.of(" ", errorInvalid)           // Blank
                , Arguments.of("AAAAAAAA", errorInvalid)    // 8 uppercase
                , Arguments.of("12345678", errorInvalid)    // 8 number
                , Arguments.of("+-*/!*;:", errorInvalid)    // 8 symbol
                , Arguments.of("A1+aaaa", errorInvalid)     // (uppercase, number, symbol) less than 8 characters
        );
    }

    @ParameterizedTest(name = "Password is invalid ({0}).")
    @MethodSource("listOfPasswordToTest")
    void password_invalid(String password, String[][] errorList) {
        // GIVEN
        // WHEN
        registerSource.setPassword(password);
        // THEN
        testConstraintViolation.checking(registerSource, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // username attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void username_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        registerSource.setUsername("alex@gmail.com");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(registerSource, errorList);
        assertThat(registerSource.getUsername()).isEqualTo("alex@gmail.com");
    }

    private static Stream<Arguments> listOfUsernameToTest() {
        String[][] errorSpace = {{"username", "{constraint.notBlank.register.username}"} , {"username", "{constraint.email.register.username}"}};
        String[][] errorEmpty = {{"username", "{constraint.notBlank.register.username}"}};
        String[][] errorNull = {{"username", "{constraint.notBlank.register.username}"}};
        String[][] errorSizeTooBig = {{"username", "{constraint.size.global}"}, {"username", "{constraint.email.register.username}"}};
        String[][] errorEmailError = {{"username", "{constraint.email.register.username}"}};

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
        registerSource.setUsername(username);
        // THEN
        testConstraintViolation.checking(registerSource, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // fullname attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void fullname_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        registerSource.setFullname("Fullname Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(registerSource, errorList);
        assertThat(registerSource.getFullname()).isEqualTo("Fullname Test");
    }

    private static Stream<Arguments> listOfFullnameToTest() {
        String[][] errorSpace = {{"fullname", "{constraint.notBlank.register.fullname}"}};
        String[][] errorEmpty = {{"fullname", "{constraint.notBlank.register.fullname}"}};
        String[][] errorNull = {{"fullname", "{constraint.notBlank.register.fullname}"}};
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
        registerSource.setFullname(fullname);
        // THEN
        testConstraintViolation.checking(registerSource, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // role attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void role_whenURoleUser_thenRoleUser() {
        // GIVEN
        // WHEN
        registerSource.setRole(UserRole.USER);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(registerSource, errorList);
        assertThat(registerSource.getRole()).isEqualTo(UserRole.USER);
    }

    @Test
    void role_whenNull_thenNull() {
        // GIVEN
        // WHEN
        registerSource.setRole(null);
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(registerSource, errorList);
        assertThat(registerSource.getRole()).isNull();
    }
}

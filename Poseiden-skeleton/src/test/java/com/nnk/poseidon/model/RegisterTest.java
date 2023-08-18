package com.nnk.poseidon.model;

import com.nnk.poseidon.data.UserData;
import jakarta.validation.Validator;
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
public class RegisterTest {

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
    // password attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void password_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        registerSource.setPassword("Test123+");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(registerSource, errorList);
        assertThat(registerSource.getPassword()).isEqualTo("Test123+");
    }

    private static Stream<Arguments> listOfPasswordToTest() {
        // Elements (enum type of vehicle)
        return Stream.of(
                Arguments.of("AAAAAAAA")        // 8 uppercase
                , Arguments.of("12345678")      // 8 number
                , Arguments.of("+-*/!*;:")      // 8 symbol
                , Arguments.of("A1+aaaa")       // (uppercase, number, symbol) less than 8 characters
        );
    }

    @ParameterizedTest(name = "Password ({0}) is invalid.")
    @MethodSource("listOfPasswordToTest")
    public void password_invalid(String password) {
        // GIVEN
        // WHEN
        registerSource.setPassword(password);
        // THEN
        String[][] errorList = {{"password", "The password must contain (at least one capital letter, at least 8 characters, at least one number and one symbol)"}};
        testConstraintViolation.checking(registerSource, errorList);
    }
}

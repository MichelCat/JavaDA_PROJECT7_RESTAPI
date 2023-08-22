package com.nnk.poseidon.model;

import com.nnk.poseidon.data.UserData;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

    @ParameterizedTest(name = "Password ({0}) is invalid.")
    @CsvSource({
            "AAAAAAAA"        // 8 uppercase
            , "12345678"      // 8 number
            , "+-*/!*;:"      // 8 symbol
            , "A1+aaaa"       // (uppercase, number, symbol) less than 8 characters
    })
    void password_invalid(String password) {
        // GIVEN
        // WHEN
        registerSource.setPassword(password);
        // THEN
        String[][] errorList = {{"password", "{constraint.passwordConstraint.global}"}};
        testConstraintViolation.checking(registerSource, errorList);
    }
}

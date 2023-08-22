package com.nnk.poseidon.model;

import com.nnk.poseidon.data.RatingData;
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
 * RatingTest is the unit test class managing the Rating
 *
 * @author MC
 * @version 1.0
 */
@SpringBootTest
@ActiveProfiles("test")
class RatingTest {

    @Autowired
    private Validator validator;

    private TestConstraintViolation<Rating> testConstraintViolation;
    private Rating rating;

    @BeforeEach
    private void setUpBefore() {
        testConstraintViolation = new TestConstraintViolation<>(validator);

        rating = RatingData.getRatingSource();
    }

    // -----------------------------------------------------------------------------------------------
    // builder method
    // -----------------------------------------------------------------------------------------------
    @Test
    void builder_TestBuildAndNew_thenEqual() {
        // GIVEN
        // WHEN
        Rating objBuild = Rating.builder()
                            .build();
        Rating objNew = new Rating();
        // THEN
        assertThat(objBuild).isEqualTo(objNew);
    }

    // -----------------------------------------------------------------------------------------------
    // moodysRating attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void moodysRating_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setMoodysRating("MoodysRating Test");
        // WHEN
        String[][] errorList = {};
        testConstraintViolation.checking(rating, errorList);
        assertThat(rating.getMoodysRating()).isEqualTo("MoodysRating Test");
    }

    private static Stream<Arguments> listOfMoodysRatingToTest() {
        String[][] errorSpace = {{"moodysRating", "{constraint.notBlank.rating.moodysRating}"}};
        String[][] errorEmpty = {{"moodysRating", "{constraint.notBlank.rating.moodysRating}"}};
        String[][] errorNull = {{"moodysRating", "{constraint.notBlank.rating.moodysRating}"}};
        String[][] errorSizeTooBig = {{"moodysRating", "{constraint.size.global}"}};

        return Stream.of(
                Arguments.of(" ", errorSpace, "space")
                , Arguments.of("", errorEmpty, "empty")
                , Arguments.of(null, errorNull, "null")
                , Arguments.of(StringUtils.repeat('a', 126), errorSizeTooBig, "size too big")
        );
    }

    @ParameterizedTest(name = "MoodysRating is {2} ({0}).")
    @MethodSource("listOfMoodysRatingToTest")
    void moodysRating_thenConstraintViolation(String moodysRating, String[][] errorList, String message) {
        // GIVEN
        // WHEN
        rating.setMoodysRating(moodysRating);
        // THEN
        testConstraintViolation.checking(rating, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // sandPRating attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void sandPRating_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setSandPRating("SandPRating Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rating, errorList);
        assertThat(rating.getSandPRating()).isEqualTo("SandPRating Test");
    }

    private static Stream<Arguments> listOfSandPRatingToTest() {
        String[][] errorSpace = {{"sandPRating", "{constraint.notBlank.rating.sandPRating}"}};
        String[][] errorEmpty = {{"sandPRating", "{constraint.notBlank.rating.sandPRating}"}};
        String[][] errorNull = {{"sandPRating", "{constraint.notBlank.rating.sandPRating}"}};
        String[][] errorSizeTooBig = {{"sandPRating", "{constraint.size.global}"}};

        return Stream.of(
                Arguments.of(" ", errorSpace, "space")
                , Arguments.of("", errorEmpty, "empty")
                , Arguments.of(null, errorNull, "null")
                , Arguments.of(StringUtils.repeat('a', 126), errorSizeTooBig, "size too big")
        );
    }

    @ParameterizedTest(name = "SandPRating is {2} ({0}).")
    @MethodSource("listOfSandPRatingToTest")
    void sandPRating_thenConstraintViolation(String sandPRating, String[][] errorList, String message) {
        // GIVEN
        // WHEN
        rating.setSandPRating(sandPRating);
        // THEN
        testConstraintViolation.checking(rating, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // fitchRating attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void fitchRating_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setFitchRating("FitchRating Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rating, errorList);
        assertThat(rating.getFitchRating()).isEqualTo("FitchRating Test");
    }

    private static Stream<Arguments> listOfFitchRatingToTest() {
        String[][] errorSpace = {{"fitchRating", "{constraint.notBlank.rating.fitchRating}"}};
        String[][] errorEmpty = {{"fitchRating", "{constraint.notBlank.rating.fitchRating}"}};
        String[][] errorNull = {{"fitchRating", "{constraint.notBlank.rating.fitchRating}"}};
        String[][] errorSizeTooBig = {{"fitchRating", "{constraint.size.global}"}};

        return Stream.of(
                Arguments.of(" ", errorSpace, "space")
                , Arguments.of("", errorEmpty, "empty")
                , Arguments.of(null, errorNull, "null")
                , Arguments.of(StringUtils.repeat('a', 126), errorSizeTooBig, "size too big")
        );
    }

    @ParameterizedTest(name = "FitchRating is {2} ({0}).")
    @MethodSource("listOfFitchRatingToTest")
    void fitchRating_thenConstraintViolation(String fitchRating, String[][] errorList, String message) {
        // GIVEN
        // WHEN
        rating.setFitchRating(fitchRating);
        // THEN
        testConstraintViolation.checking(rating, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // orderNumber attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void orderNumber_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        rating.setOrderNumber(10);
        // WHEN
        String[][] errorList = {};
        testConstraintViolation.checking(rating, errorList);
        assertThat(rating.getOrderNumber()).isEqualTo(10);
    }

    @Test
    void orderNumber_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setOrderNumber(null);
        // THEN
        String[][] errorList = {{"orderNumber", "{constraint.notNull.rating.orderNumber}"}};
        testConstraintViolation.checking(rating, errorList);
    }
}

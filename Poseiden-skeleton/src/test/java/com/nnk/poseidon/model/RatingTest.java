package com.nnk.poseidon.model;

import com.nnk.poseidon.data.RatingData;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * RatingTest is the unit test class managing the Rating
 *
 * @author MC
 * @version 1.0
 */
@SpringBootTest
@ActiveProfiles("test")
public class RatingTest {

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
    public void builder_TestBuildAndNew_thenEqual() {
        // GIVEN
        // WHEN
        Rating objBuild = Rating.builder()
                            .build();
        Rating objNew = new Rating();
        // THEN
        assertThat(objBuild).usingRecursiveComparison().isEqualTo(objNew);
    }

    // -----------------------------------------------------------------------------------------------
    // moodysRating attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void moodysRating_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setMoodysRating("MoodysRating Test");
        // WHEN
        String[][] errorList = {};
        testConstraintViolation.checking(rating, errorList);
        assertThat(rating.getMoodysRating()).isEqualTo("MoodysRating Test");
    }

    @Test
    public void moodysRating_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setMoodysRating(" ");
        // THEN
        String[][] errorList = {{"moodysRating", "Moodys rating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void moodysRating_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setMoodysRating("");
        // THEN
        String[][] errorList = {{"moodysRating", "Moodys rating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void moodysRating_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setMoodysRating(null);
        // THEN
        String[][] errorList = {{"moodysRating", "Moodys rating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void moodysRating_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setMoodysRating(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"moodysRating", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(rating, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // sandPRating attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void sandPRating_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setSandPRating("SandPRating Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rating, errorList);
        assertThat(rating.getSandPRating()).isEqualTo("SandPRating Test");
    }

    @Test
    public void sandPRating_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setSandPRating(" ");
        // THEN
        String[][] errorList = {{"sandPRating", "Sand PRating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void sandPRating_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setSandPRating("");
        // THEN
        String[][] errorList = {{"sandPRating", "Sand PRating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void sandPRating_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setSandPRating(null);
        // THEN
        String[][] errorList = {{"sandPRating", "Sand PRating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void sandPRating_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setSandPRating(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"sandPRating", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(rating, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // fitchRating attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void fitchRating_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setFitchRating("FitchRating Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rating, errorList);
        assertThat(rating.getFitchRating()).isEqualTo("FitchRating Test");
    }

    @Test
    public void fitchRating_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setFitchRating(" ");
        // THEN
        String[][] errorList = {{"fitchRating", "Fitch rating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void fitchRating_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setFitchRating("");
        // THEN
        String[][] errorList = {{"fitchRating", "Fitch rating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void fitchRating_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setFitchRating(null);
        // THEN
        String[][] errorList = {{"fitchRating", "Fitch rating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void fitchRating_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setFitchRating(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"fitchRating", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(rating, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // orderNumber attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void orderNumber_normal_thenNoConstraintViolations() {
        // GIVEN
        // WHEN
        rating.setOrderNumber(10);
        // WHEN
        String[][] errorList = {};
        testConstraintViolation.checking(rating, errorList);
        assertThat(rating.getOrderNumber()).isEqualTo(10);
    }

    @Test
    public void orderNumber_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rating.setOrderNumber(null);
        // THEN
        String[][] errorList = {{"orderNumber", "Order number must not be null"}};
        testConstraintViolation.checking(rating, errorList);
    }
}

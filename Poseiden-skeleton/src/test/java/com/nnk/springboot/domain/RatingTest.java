package com.nnk.springboot.domain;

import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


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

        rating = Rating.builder()
                .moodysRating("Moodys Rating")
                .sandPRating("Sand PRating")
                .fitchRating("Fitch Rating")
                .orderNumber(10)
                .build();
    }

    // -----------------------------------------------------------------------------------------------
    // moodysRating attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalMoodysRating_thenNoConstraintViolation() {
        // GIVEN
        rating.setMoodysRating("MoodysRating Test");
        // WHEN
        String[][] errorList = {};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void whenBlankMoodysRating_thenOneConstraintViolation() {
        // GIVEN
        rating.setMoodysRating(" ");
        // WHEN
        String[][] errorList = {{"moodysRating", "Moodys rating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void whenEmptyMoodysRating_thenOneConstraintViolation() {
        // GIVEN
        rating.setMoodysRating("");
        // WHEN
        String[][] errorList = {{"moodysRating", "Moodys rating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void whenNullMoodysRating_thenOneConstraintViolation() {
        // GIVEN
        rating.setMoodysRating(null);
        // WHEN
        String[][] errorList = {{"moodysRating", "Moodys rating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void whenSizeMoodysRatingTooBig_thenOneConstraintViolation() {
        // GIVEN
        rating.setMoodysRating(StringUtils.repeat('a', 126));
        // WHEN
        String[][] errorList = {{"moodysRating", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(rating, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // sandPRating attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalSandPRating_thenNoConstraintViolation() {
        // GIVEN
        rating.setSandPRating("SandPRating Test");
        // WHEN
        String[][] errorList = {};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void whenBlankSandPRating_thenOneConstraintViolation() {
        // GIVEN
        rating.setSandPRating(" ");
        // WHEN
        String[][] errorList = {{"sandPRating", "Sand PRating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void whenEmptySandPRating_thenOneConstraintViolation() {
        // GIVEN
        rating.setSandPRating("");
        // WHEN
        String[][] errorList = {{"sandPRating", "Sand PRating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void whenNullSandPRating_thenOneConstraintViolation() {
        // GIVEN
        rating.setSandPRating(null);
        // WHEN
        String[][] errorList = {{"sandPRating", "Sand PRating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void whenSizeSandPRatingTooBig_thenOneConstraintViolation() {
        // GIVEN
        rating.setSandPRating(StringUtils.repeat('a', 126));
        // WHEN
        String[][] errorList = {{"sandPRating", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(rating, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // fitchRating attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalFitchRating_thenNoConstraintViolation() {
        // GIVEN
        rating.setFitchRating("FitchRating Test");
        // WHEN
        String[][] errorList = {};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void whenBlankFitchRating_thenOneConstraintViolation() {
        // GIVEN
        rating.setFitchRating(" ");
        // WHEN
        String[][] errorList = {{"fitchRating", "Fitch rating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void whenEmptyFitchRating_thenOneConstraintViolation() {
        // GIVEN
        rating.setFitchRating("");
        // WHEN
        String[][] errorList = {{"fitchRating", "Fitch rating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void whenNullFitchRating_thenOneConstraintViolation() {
        // GIVEN
        rating.setFitchRating(null);
        // WHEN
        String[][] errorList = {{"fitchRating", "Fitch rating is mandatory"}};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void whenSizeFitchRatingTooBig_thenOneConstraintViolation() {
        // GIVEN
        rating.setFitchRating(StringUtils.repeat('a', 126));
        // WHEN
        String[][] errorList = {{"fitchRating", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(rating, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // orderNumber attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void whenNormalOrderNumber_thenNoConstraintViolations() {
        // GIVEN
        rating.setOrderNumber(10);
        // WHEN
        String[][] errorList = {};
        testConstraintViolation.checking(rating, errorList);
    }

    @Test
    public void whenNullOrderNumber_thenOneConstraintViolation() {
        // GIVEN
        rating.setOrderNumber(null);
        // WHEN
        String[][] errorList = {{"orderNumber", "Order number must not be null"}};
        testConstraintViolation.checking(rating, errorList);
    }
}

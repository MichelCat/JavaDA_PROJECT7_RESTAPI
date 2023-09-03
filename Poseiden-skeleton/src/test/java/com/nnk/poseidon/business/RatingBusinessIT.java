package com.nnk.poseidon.business;

import com.nnk.poseidon.data.RatingData;
import com.nnk.poseidon.model.Rating;
import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.data.GlobalData;
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
 * RatingBusinessIT is a class of integration tests on Ratings service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RatingBusinessIT {

    @Autowired
    private RatingBusiness ratingBusiness;
    @Autowired
    private MessageSource messageSource;

    private TestMessageSource testMessageSource;
    private Rating ratingSource;
    private Rating ratingSave;
    private Integer ratingId;


    @BeforeEach
    public void setUpBefore() {
        testMessageSource = new TestMessageSource(messageSource);

        ratingSource = RatingData.getRatingSource();
        ratingSave = RatingData.getRatingSave();

        ratingId = ratingSave.getId();
    }

    // -----------------------------------------------------------------------------------------------
    // getRatingsList method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    void getRatingsList_findAllNormal() {
        // GIVEN
        // WHEN
        List<Rating> result = ratingBusiness.getRatingsList();
        // THEN
        assertThat(result).hasSize(1)
                            .contains(ratingSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void getRatingsList_findAllEmpty() {
        // GIVEN
        // WHEN
        List<Rating> result = ratingBusiness.getRatingsList();
        // THEN
        assertThat(result).isEmpty();
    }

    // -----------------------------------------------------------------------------------------------
    // createRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void createRating_ratingNotExist() throws MyException {
        // GIVEN
        // WHEN
        Rating result = ratingBusiness.createRating(ratingSource);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("creationDate")
                .isEqualTo(ratingSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    void createRating_ratingExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.createRating(ratingSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rating.ratingExists", new Object[] { ratingSave.getId() });
    }

    // -----------------------------------------------------------------------------------------------
    // getRatingById method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    void getRatingById_ratingExist() throws MyException {
        // GIVEN
        // WHEN
        assertThat(ratingBusiness.getRatingById(ratingId)).isEqualTo(ratingSave);
        // THEN
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void getRatingById_ratingNotExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.getRatingById(ratingId));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rating.unknown", new Object[] { ratingId });
    }

    // -----------------------------------------------------------------------------------------------
    // updateRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    void updateRating_ratingExist() throws MyException {
        // GIVEN
        // WHEN
        Rating result = ratingBusiness.updateRating(ratingId, ratingSave);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("asOfDate")
                .isEqualTo(ratingSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void updateRating_ratingNotExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.updateRating(ratingId, ratingSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rating.unknown", new Object[] { ratingId });
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    void deleteRating_ratingExist() throws MyException {
        // GIVEN
        // WHEN
        ratingBusiness.deleteRating(ratingId);
        // THEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.getRatingById(ratingId));
        testMessageSource.compare(exception
                            , "exception.rating.unknown", new Object[] { ratingId });
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void deleteRating_ratingNotExist() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.deleteRating(ratingId));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rating.unknown", new Object[] { ratingId });
    }
}

package com.nnk.poseidon.business;

import com.nnk.poseidon.data.RatingData;
import com.nnk.poseidon.model.Rating;
import com.nnk.poseidon.exception.MyExceptionBadRequestException;
import com.nnk.poseidon.exception.MyExceptionNotFoundException;
import com.nnk.poseidon.data.GlobalData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    private Rating ratingSource;
    private Rating ratingSave;
    private Integer ratingId;


    @BeforeEach
    public void setUpBefore() {
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
    void createRating_ratingNotExist() {
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
        assertThrows(MyExceptionBadRequestException.class, () -> ratingBusiness.createRating(ratingSave));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // getRatingById method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    void getRatingById_ratingExist() {
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
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.getRatingById(ratingId));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // updateRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    void updateRating_ratingExist() {
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
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.updateRating(ratingId, ratingSave));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    void deleteRating_ratingExist() {
        // GIVEN
        // WHEN
        ratingBusiness.deleteRating(ratingId);
        // THEN
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.getRatingById(ratingId));
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    void deleteRating_ratingNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.deleteRating(ratingId));
        // THEN
    }
}

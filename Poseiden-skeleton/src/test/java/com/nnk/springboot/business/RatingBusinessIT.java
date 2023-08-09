package com.nnk.springboot.business;

import com.nnk.springboot.data.RatingData;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.MyExceptionBadRequestException;
import com.nnk.springboot.exception.MyExceptionNotFoundException;
import com.nnk.springboot.data.GlobalData;
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
public class RatingBusinessIT {

    @Autowired
    private RatingBusiness ratingBusiness;

    private Rating ratingSource;
    private Rating ratingSave;


    @BeforeEach
    public void setUpBefore() {
        ratingSource = RatingData.getRatingSource();
        ratingSave = RatingData.getRatingSave();
    }

    // -----------------------------------------------------------------------------------------------
    // getRatingsList method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    public void getRatingsList_findAllNormal() {
        // GIVEN
        // WHEN
        List<Rating> result = ratingBusiness.getRatingsList();
        // THEN
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(ratingSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void getRatingsList_findAllEmpty() {
        // GIVEN
        // WHEN
        List<Rating> result = ratingBusiness.getRatingsList();
        // THEN
        assertThat(result.size()).isEqualTo(0);
    }

    // -----------------------------------------------------------------------------------------------
    // createRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void createRating_ratingNotExist() {
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
    public void createRating_ratingExist() {
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
    public void getRatingById_ratingExist() {
        // GIVEN
        // WHEN
        assertThat(ratingBusiness.getRatingById(ratingSave.getId())).isEqualTo(ratingSave);
        // THEN
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void getRatingById_ratingNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.getRatingById(ratingSave.getId()));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // updateRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    public void updateRating_ratingExist() {
        // GIVEN
        // WHEN
        Rating result = ratingBusiness.updateRating(ratingSave.getId(), ratingSave);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("asOfDate")
                .isEqualTo(ratingSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void updateRating_ratingNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.updateRating(ratingSave.getId(), ratingSave));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RatingData.scriptCreateRating)
    public void deleteRating_ratingExist() {
        // GIVEN
        // WHEN
        ratingBusiness.deleteRating(ratingSave.getId());
        // THEN
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.getRatingById(ratingSave.getId()));
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void deleteRating_ratingNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.deleteRating(ratingSave.getId()));
        // THEN
    }
}

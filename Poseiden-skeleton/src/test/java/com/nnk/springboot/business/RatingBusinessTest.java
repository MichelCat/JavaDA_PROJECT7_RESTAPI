package com.nnk.springboot.business;

import com.nnk.springboot.data.RatingData;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.MyExceptionBadRequestException;
import com.nnk.springboot.exception.MyExceptionNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * RatingBusinessTest is a unit testing class of the ratings service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class RatingBusinessTest {

    @Autowired
    private RatingBusiness ratingBusiness;

    @MockBean
    private RatingRepository ratingRepository;

    private Rating ratingSource;
    private Rating ratingSave;
    public List<Rating> ratingsList;


    @BeforeEach
    public void setUpBefore() {
        ratingSource = RatingData.getRatingSource();
        ratingSave = RatingData.getRatingSave();

        ratingsList = new ArrayList<>();
        ratingsList.add(ratingSave);
    }

    // -----------------------------------------------------------------------------------------------
    // getRatingsList method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void getRatingsList_findAllNormal() {
        // GIVEN
        when(ratingRepository.findAll()).thenReturn(ratingsList);
        // WHEN
        assertThat(ratingBusiness.getRatingsList()).isEqualTo(ratingsList);
        // THEN
        verify(ratingRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getRatingsList_findAllEmpty() {
        // GIVEN
        when(ratingRepository.findAll()).thenReturn(new ArrayList<>());
        // WHEN
        assertThat(ratingBusiness.getRatingsList()).isEmpty();
        // THEN
        verify(ratingRepository, Mockito.times(1)).findAll();
    }

    // -----------------------------------------------------------------------------------------------
    // createRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void createRating_saveNormal() {
        // GIVEN
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(ratingRepository.save(ratingSource)).thenReturn(ratingSave);
        // WHEN
        assertThat(ratingBusiness.createRating(ratingSource)).isEqualTo(ratingSave);
        // THEN
        verify(ratingRepository, Mockito.times(1)).save(any(Rating.class));
    }

    @Test
    public void createRating_nullRatingParameter_returnNullPointer() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> ratingBusiness.createRating(null));
        // THEN
        verify(ratingRepository, Mockito.times(0)).save(any(Rating.class));
    }

    @Test
    public void createRating_ratingExist_returnBadRequest() {
        // GIVEN
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(ratingSave));
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> ratingBusiness.createRating(ratingSave));
        // THEN
        verify(ratingRepository, Mockito.times(0)).save(any(Rating.class));
    }

    // -----------------------------------------------------------------------------------------------
    // getRatingById method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void getRatingById_findByIdNormal() {
        // GIVEN
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(ratingSave));
        // WHEN
        assertThat(ratingBusiness.getRatingById(1)).isEqualTo(ratingSave);
        // THEN
        verify(ratingRepository, Mockito.times(1)).findById(any(Integer.class));
    }

    @Test
    public void getRatingById_nullIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.getRatingById(null));
        // THEN
        verify(ratingRepository, Mockito.times(1)).findById(null);
    }

    // -----------------------------------------------------------------------------------------------
    // updateRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void updateRating_updateNormal() {
        // GIVEN
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(ratingSave));
        when(ratingRepository.save(ratingSave)).thenReturn(ratingSave);
        // WHEN
        assertThat(ratingBusiness.updateRating(1, ratingSource)).isEqualTo(ratingSave);
        // THEN
        verify(ratingRepository, Mockito.times(1)).save(any(Rating.class));
    }

    @Test
    public void updateRating_ratingNotExist_returnNotFound() {
        // GIVEN
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.updateRating(2, ratingSource));
        // THEN
        verify(ratingRepository, Mockito.times(0)).save(any(Rating.class));
    }

    @Test
    public void updateRating_nullIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.updateRating(null, ratingSource));
        // THEN
        verify(ratingRepository, Mockito.times(0)).save(any(Rating.class));
    }

    @Test
    public void updateRating_zeroIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.updateRating(0, ratingSource));
        // THEN
        verify(ratingRepository, Mockito.times(0)).save(any(Rating.class));
    }

    @Test
    public void updateRating_nullRatingParameter_returnNotFound() {
        // GIVEN
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(ratingSave));
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> ratingBusiness.updateRating(1, null));
        // THEN
        verify(ratingRepository, Mockito.times(0)).save(any(Rating.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void deleteRating_deleteNormal() {
        // GIVEN
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(ratingSave));
        doNothing().when(ratingRepository).deleteById(any(Integer.class));
        // WHEN
        ratingBusiness.deleteRating(1);
        // THEN
        verify(ratingRepository, Mockito.times(1)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteRating_ratingNotExist_returnNotFound() {
        // GIVEN
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.deleteRating(2));
        // THEN
        verify(ratingRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteRating_nullIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.deleteRating(null));
        // THEN
        verify(ratingRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteRating_zeroIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ratingBusiness.deleteRating(0));
        // THEN
        verify(ratingRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }
}

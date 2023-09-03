package com.nnk.poseidon.business;

import com.nnk.poseidon.data.RatingData;
import com.nnk.poseidon.model.Rating;
import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
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
class RatingBusinessTest {

    @Autowired
    private RatingBusiness ratingBusiness;
    @Autowired
    private MessageSource messageSource;

    @MockBean
    private RatingRepository ratingRepository;

    private TestMessageSource testMessageSource;
    private Rating ratingSource;
    private Rating ratingSave;
    public List<Rating> ratingsList;


    @BeforeEach
    public void setUpBefore() {
        testMessageSource = new TestMessageSource(messageSource);

        ratingSource = RatingData.getRatingSource();
        ratingSave = RatingData.getRatingSave();

        ratingsList = new ArrayList<>();
        ratingsList.add(ratingSave);
    }

    // -----------------------------------------------------------------------------------------------
    // getRatingsList method
    // -----------------------------------------------------------------------------------------------
    @Test
    void getRatingsList_findAllNormal() {
        // GIVEN
        when(ratingRepository.findAll()).thenReturn(ratingsList);
        // WHEN
        assertThat(ratingBusiness.getRatingsList()).isEqualTo(ratingsList);
        // THEN
        verify(ratingRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getRatingsList_findAllEmpty() {
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
    void createRating_saveNormal() throws MyException {
        // GIVEN
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(ratingRepository.save(ratingSource)).thenReturn(ratingSave);
        // WHEN
        assertThat(ratingBusiness.createRating(ratingSource)).isEqualTo(ratingSave);
        // THEN
        verify(ratingRepository, Mockito.times(1)).save(any(Rating.class));
    }

    @Test
    void createRating_nullRatingParameter_returnNullPointer() {
        // GIVEN
        when(ratingRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.createRating(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rating.nullRating", null);
        verify(ratingRepository, Mockito.times(0)).save(any(Rating.class));
    }

    @Test
    void createRating_ratingExist_returnMyException() {
        // GIVEN
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(ratingSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.createRating(ratingSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rating.ratingExists", new Object[] { ratingSave.getId() });
        verify(ratingRepository, Mockito.times(0)).save(any(Rating.class));
    }

    // -----------------------------------------------------------------------------------------------
    // getRatingById method
    // -----------------------------------------------------------------------------------------------
    @Test
    void getRatingById_findByIdNormal() throws MyException {
        // GIVEN
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(ratingSave));
        // WHEN
        assertThat(ratingBusiness.getRatingById(1)).isEqualTo(ratingSave);
        // THEN
        verify(ratingRepository, Mockito.times(1)).findById(any(Integer.class));
    }

    @Test
    void getRatingById_nullIdParameter_returnMyException() {
        // GIVEN
        when(ratingRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.getRatingById(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rating.unknown", new Object[] { null });
        verify(ratingRepository, Mockito.times(1)).findById(null);
    }

    // -----------------------------------------------------------------------------------------------
    // updateRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    void updateRating_updateNormal() throws MyException {
        // GIVEN
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(ratingSave));
        when(ratingRepository.save(ratingSave)).thenReturn(ratingSave);
        // WHEN
        assertThat(ratingBusiness.updateRating(1, ratingSource)).isEqualTo(ratingSave);
        // THEN
        verify(ratingRepository, Mockito.times(1)).save(any(Rating.class));
    }

    @Test
    void updateRating_ratingNotExist_returnMyException() {
        // GIVEN
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.updateRating(2, ratingSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rating.unknown", new Object[] { 2 });
        verify(ratingRepository, Mockito.times(0)).save(any(Rating.class));
    }

    @Test
    void updateRating_nullIdParameter_returnMyException() {
        // GIVEN
        when(ratingRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.updateRating(null, ratingSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rating.unknown", new Object[] { null });
        verify(ratingRepository, Mockito.times(0)).save(any(Rating.class));
    }

    @Test
    void updateRating_zeroIdParameter_returnMyException() {
        // GIVEN
        when(ratingRepository.findById(0)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.updateRating(0, ratingSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rating.unknown", new Object[] { 0 });
        verify(ratingRepository, Mockito.times(0)).save(any(Rating.class));
    }

    @Test
    void updateRating_nullRatingParameter_returnMyException() {
        // GIVEN
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(ratingSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.updateRating(1, null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rating.nullRating", null);
        verify(ratingRepository, Mockito.times(0)).save(any(Rating.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRating method
    // -----------------------------------------------------------------------------------------------
    @Test
    void deleteRating_deleteNormal() throws MyException {
        // GIVEN
        when(ratingRepository.existsById(any(Integer.class))).thenReturn(true);
        doNothing().when(ratingRepository).deleteById(any(Integer.class));
        // WHEN
        ratingBusiness.deleteRating(1);
        // THEN
        verify(ratingRepository, Mockito.times(1)).deleteById(any(Integer.class));
    }

    @Test
    void deleteRating_ratingNotExist_returnMyException() {
        // GIVEN
        when(ratingRepository.existsById(any(Integer.class))).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.deleteRating(2));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rating.unknown", new Object[] { 2 });
        verify(ratingRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    void deleteRating_nullIdParameter_returnMyException() {
        // GIVEN
        when(ratingRepository.existsById(null)).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.deleteRating(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rating.unknown", new Object[] { null });
        verify(ratingRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    void deleteRating_zeroIdParameter_returnMyException() {
        // GIVEN
        when(ratingRepository.existsById(0)).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ratingBusiness.deleteRating(0));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rating.unknown", new Object[] { 0 });
        verify(ratingRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }
}

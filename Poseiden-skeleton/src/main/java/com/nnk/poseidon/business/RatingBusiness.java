package com.nnk.poseidon.business;

import com.nnk.poseidon.domain.Rating;
import com.nnk.poseidon.exception.MyExceptionBadRequestException;
import com.nnk.poseidon.exception.MyExceptionNotFoundException;
import com.nnk.poseidon.repositories.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * RatingBusiness is the Ratings page processing service
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Service
public class RatingBusiness {

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Find list of Ratings
     *
     * @return List of Ratings
     */
    public List<Rating> getRatingsList() {
        return ratingRepository.findAll();
    }

    /**
     * Create new Rating
     *
     * @param rating The Rating object added
     *
     * @return Rating added
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public Rating createRating(final Rating rating)
            throws MyExceptionBadRequestException {
        // Rating parameter is null
        if (rating == null) {
            log.debug("THROW, Rating is null.");
            throw new MyExceptionBadRequestException("throw.rating.nullRating");
        }
        // Rating exist
        Integer id = rating.getId();
        if (id != null) {
            Optional<Rating> optRatingEntity = ratingRepository.findById(id);
            if (optRatingEntity.isPresent()) {
                log.debug("THROW, Rating exist ({}).", optRatingEntity.get());
                throw new MyExceptionBadRequestException("throw.rating.ratingExists");
            }
        }
        // Rating saved
        return ratingRepository.save(rating);
    }

    /**
     * Find Rating
     *
     * @param id Rating ID founded
     *
     * @return Rating founded
     * @throws MyExceptionNotFoundException Exception not found
     */
    public Rating getRatingById(final Integer id)
            throws MyExceptionNotFoundException {
        // Rating does not exist
        Optional<Rating> optRatingEntity = ratingRepository.findById(id);
        if (optRatingEntity.isPresent() == false) {
            log.debug("THROW, Rating not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.rating.unknown", id);
        }
        // Rating found
        return optRatingEntity.get();
    }

    /**
     * Updated Rating
     *
     * @param id Rating ID updated
     * @param rating The Rating object updated
     *
     * @return Rating updated
     * @throws MyExceptionNotFoundException Exception not found
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public Rating updateRating(final Integer id
            , final Rating rating)
            throws MyExceptionNotFoundException {
        // Rating does not exist
        Optional<Rating> optRatingEntity = ratingRepository.findById(id);
        if (optRatingEntity.isPresent() == false) {
            log.debug("THROW, Rating not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.rating.unknown", id);
        }
        // Rating parameter is null
        if (rating == null) {
            log.debug("THROW, Rating is null.");
            throw new MyExceptionBadRequestException("throw.rating.nullRating");
        }
        // Rating updated
        Rating ratingEntity = optRatingEntity.get();
        ratingEntity.setMoodysRating(rating.getMoodysRating());
        ratingEntity.setSandPRating(rating.getSandPRating());
        ratingEntity.setFitchRating(rating.getFitchRating());
        ratingEntity.setOrderNumber(rating.getOrderNumber());
        return ratingRepository.save(ratingEntity);
    }

    /**
     * Deleted Rating
     *
     * @param id Rating ID deleted
     *
     * @return void
     * @throws MyExceptionNotFoundException Exception not found
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteRating(final Integer id)
            throws MyExceptionNotFoundException {
        // Rating does not exist
        Optional<Rating> optRatingEntity = ratingRepository.findById(id);
        if (optRatingEntity.isPresent() == false) {
            log.debug("THROW, Rating not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.rating.unknown", id);
        }
        // Rating deleted
        ratingRepository.deleteById(id);
    }
}

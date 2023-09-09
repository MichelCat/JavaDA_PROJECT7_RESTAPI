package com.nnk.poseidon.business;

import com.nnk.poseidon.model.Rating;
import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.repository.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    @Autowired
    private MessageSource messageSource;

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
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Rating createRating(final Rating rating)
            throws MyException {
        // Rating parameter is null
        if (rating == null) {
            String msgSource = messageSource.getMessage("exception.rating.nullRating"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Rating exist
        Integer id = rating.getId();
        if (id != null) {
            Optional<Rating> optRatingEntity = ratingRepository.findById(id);
            if (optRatingEntity.isPresent()) {
                String msgSource = messageSource.getMessage("exception.rating.ratingExists"
                                    , new Object[] { id }
                                    , LocaleContextHolder.getLocale());
                log.debug("Exception, " + msgSource);
                throw new MyException(msgSource);
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
     * @throws MyException Exception
     */
    public Rating getRatingById(final Integer id)
            throws MyException {
        // Rating does not exist
        Optional<Rating> optRatingEntity = ratingRepository.findById(id);
        if (optRatingEntity.isEmpty()) {
            String msgSource = messageSource.getMessage("exception.rating.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
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
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Rating updateRating(final Integer id
            , final Rating rating)
            throws MyException {
        // Rating does not exist
        Optional<Rating> optRatingEntity = ratingRepository.findById(id);
        if (optRatingEntity.isEmpty()) {
            String msgSource = messageSource.getMessage("exception.rating.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Rating parameter is null
        if (rating == null) {
            String msgSource = messageSource.getMessage("exception.rating.nullRating"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
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
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteRating(final Integer id)
            throws MyException {
        // Rating does not exist
        if (ratingRepository.existsById(id) == false) {
            String msgSource = messageSource.getMessage("exception.rating.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Rating deleted
        ratingRepository.deleteById(id);
    }
}

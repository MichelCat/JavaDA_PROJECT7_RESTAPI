package com.nnk.poseidon.repository;

import com.nnk.poseidon.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RatingRepository is the interface that manages Rating
 *
 * @author MC
 * @version 1.0
 */
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}

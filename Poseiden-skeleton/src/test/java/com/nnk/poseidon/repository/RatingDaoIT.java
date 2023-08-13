package com.nnk.poseidon.repository;

import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.RatingData;
import com.nnk.poseidon.model.Rating;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * RatingDaoIT is the integration test class handling Rating
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class RatingDaoIT {

	@Autowired
	private RatingRepository ratingRepository;

	@Test
	@Sql(scripts = GlobalData.scriptClearDataBase)
	public void ratingTest() {
		Rating oldRating;
		Rating rating = RatingData.getRatingSource();

		// Save
		oldRating = rating;
		rating = ratingRepository.save(rating);
		assertThat(rating).isNotNull();
		assertThat(rating).usingRecursiveComparison().ignoringFields("id").isEqualTo(oldRating);
		assertThat(rating.getId()).isEqualTo(1);

		// Update
		rating.setOrderNumber(20);
		oldRating = rating;
		rating = ratingRepository.save(rating);
		assertThat(rating).isNotNull();
		assertThat(rating).usingRecursiveComparison().isEqualTo(oldRating);

		// Find
		List<Rating> listResult = ratingRepository.findAll();
		assertThat(listResult).isNotNull();
		assertThat(listResult).hasSize(1);
		assertThat(listResult.get(0)).usingRecursiveComparison().isEqualTo(rating);

		// Delete
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> optRating = ratingRepository.findById(id);
		assertThat(optRating).isEmpty();
	}
}

package com.nnk.springboot.repositories;

import com.nnk.springboot.data.BidData;
import com.nnk.springboot.data.RatingData;
import com.nnk.springboot.domain.Rating;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class RatingDaoIT {

	@Autowired
	private RatingRepository ratingRepository;

	@Test
	@Sql(scripts = BidData.scriptClearDataBase)
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

package com.nnk.poseidon.repository;

import com.nnk.poseidon.data.BidData;
import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.model.Bid;
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
import static org.assertj.core.api.Assertions.withPrecision;


/**
 * BidDaoIT is the integration test class handling Bid
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class BidDaoIT {

	@Autowired
	private BidRepository bidRepository;

	@Test
	@Sql(scripts = GlobalData.scriptClearDataBase)
	public void bidListTest() {
		Bid oldBid;
		Bid bid = BidData.getBidSource();

		// Save
		oldBid = bid;
		bid = bidRepository.save(bid);
		assertThat(bid).isNotNull();
		assertThat(bid.getBidListId()).isNotNull();
		assertThat(bid).usingRecursiveComparison().ignoringFields("bidListId").isEqualTo(oldBid);
		assertThat(bid.getBidListId()).isEqualTo(1);
		assertThat(bid.getBidQuantity()).isEqualTo(10d, withPrecision(0.001d));

		// Update
		bid.setBidQuantity(20d);
		oldBid = bid;
		bid = bidRepository.save(bid);
		assertThat(bid).isNotNull();
		assertThat(bid).usingRecursiveComparison().isEqualTo(oldBid);
		assertThat(bid.getBidQuantity()).isEqualTo(20d, withPrecision(0.001d));

		// Find
		List<Bid> listResult = bidRepository.findAll();
		assertThat(listResult).isNotNull();
		assertThat(listResult).hasSize(1);
		assertThat(listResult.get(0)).usingRecursiveComparison().isEqualTo(bid);

		// Delete
		Integer id = bid.getBidListId();
		bidRepository.delete(bid);
		Optional<Bid> optBid = bidRepository.findById(id);
		assertThat(optBid).isEmpty();
	}
}

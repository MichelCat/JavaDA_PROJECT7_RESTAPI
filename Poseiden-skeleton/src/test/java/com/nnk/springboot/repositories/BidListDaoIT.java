package com.nnk.springboot.repositories;

import com.nnk.springboot.data.BidData;
import com.nnk.springboot.data.GlobalData;
import com.nnk.springboot.domain.BidList;
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


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class BidListDaoIT {

	@Autowired
	private BidListRepository bidListRepository;

	/**
	 * bidListTest is the integration test class handling BidList
	 *
	 * @author MC
	 * @version 1.0
	 */
	@Test
	@Sql(scripts = GlobalData.scriptClearDataBase)
	public void bidListTest() {
		BidList oldBid;
		BidList bid = BidData.getBidSource();

		// Save
		oldBid = bid;
		bid = bidListRepository.save(bid);
		assertThat(bid).isNotNull();
		assertThat(bid.getBidListId()).isNotNull();
		assertThat(bid).usingRecursiveComparison().ignoringFields("BidListId").isEqualTo(oldBid);
		assertThat(bid.getBidListId()).isEqualTo(1);
		assertThat(bid.getBidQuantity()).isEqualTo(10d, withPrecision(0.001d));

		// Update
		bid.setBidQuantity(20d);
		oldBid = bid;
		bid = bidListRepository.save(bid);
		assertThat(bid).isNotNull();
		assertThat(bid).usingRecursiveComparison().isEqualTo(oldBid);
		assertThat(bid.getBidQuantity()).isEqualTo(20d, withPrecision(0.001d));

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		assertThat(listResult).isNotNull();
		assertThat(listResult).hasSize(1);
		assertThat(listResult.get(0)).usingRecursiveComparison().isEqualTo(bid);

		// Delete
		Integer id = bid.getBidListId();
		bidListRepository.delete(bid);
		Optional<BidList> optBid = bidListRepository.findById(id);
		assertThat(optBid).isEmpty();
	}
}

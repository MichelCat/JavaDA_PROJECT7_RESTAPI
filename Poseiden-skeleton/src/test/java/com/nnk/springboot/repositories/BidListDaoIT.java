package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.assertj.core.data.Offset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BidListDaoIT {

	@Autowired
	private BidListRepository bidListRepository;

	@Test
	public void bidListTest() {
		BidList bid = BidList.builder()
						.account("Account Test")
						.type("Type Test")
						.bidQuantity(10d)
						.build();

		// Save
		bid = bidListRepository.save(bid);
//		Assert.assertNotNull(bid.getBidListId());
//		Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);
		assertThat(bid).isNotNull();
		assertThat(bid.getBidListId()).isNotNull();
		assertThat(bid.getBidQuantity()).isEqualTo(10d);
		assertThat(bid.getBidQuantity()).isCloseTo(10d, Offset.offset(0.001d));

		// Update
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
//		Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);
		assertThat(bid).isNotNull();
		assertThat(bid.getBidQuantity()).isEqualTo(20d);
		assertThat(bid.getBidQuantity()).isCloseTo(20d, Offset.offset(0.001d));

		// Find
		List<BidList> listResult = bidListRepository.findAll();
//		Assert.assertTrue(listResult.size() > 0);
		assertThat(listResult).isNotNull();
		assertThat(listResult).hasSize(1);

		// Delete
		Integer id = bid.getBidListId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
//		Assert.assertFalse(bidList.isPresent());
		assertThat(bidList).isEmpty();
	}
}

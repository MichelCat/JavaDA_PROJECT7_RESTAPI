package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
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
public class TradeIT {

	@Autowired
	private TradeRepository tradeRepository;

	@Test
	public void tradeTest() {
		Trade trade = Trade.builder()
				.account("Trade Account")
				.type("Type")
				.buyQuantity(0d)
				.build();

		// Save
		trade = tradeRepository.save(trade);
//		Assert.assertNotNull(trade.getTradeId());
//		Assert.assertTrue(trade.getAccount().equals("Trade Account"));
		assertThat(trade).isNotNull();
		assertThat(trade.getTradeId()).isNotNull();
		assertThat(trade.getAccount()).isEqualTo("Trade Account");

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
//		Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));
		assertThat(trade).isNotNull();
		assertThat(trade.getAccount()).isEqualTo("Trade Account Update");

		// Find
		List<Trade> listResult = tradeRepository.findAll();
//		Assert.assertTrue(listResult.size() > 0);
		assertThat(listResult).isNotNull();
		assertThat(listResult).hasSize(1);

		// Delete
		Integer id = trade.getTradeId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
//		Assert.assertFalse(tradeList.isPresent());
		assertThat(tradeList).isEmpty();
	}
}

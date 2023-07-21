package com.nnk.springboot.repositories;

import com.nnk.springboot.data.BidData;
import com.nnk.springboot.data.TradeData;
import com.nnk.springboot.domain.Trade;
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
public class TradeDaoIT {

	@Autowired
	private TradeRepository tradeRepository;

	@Test
	@Sql(scripts = BidData.scriptClearDataBase)
	public void tradeTest() {
		Trade oldTrade;
		Trade trade = TradeData.getTradeSource();

		// Save
		oldTrade = trade;
		trade = tradeRepository.save(trade);
		assertThat(trade).isNotNull();
		assertThat(trade).usingRecursiveComparison().ignoringFields("id").isEqualTo(oldTrade);
		assertThat(trade.getTradeId()).isEqualTo(1);

		// Update
		trade.setAccount("Trade Account Update");
		oldTrade = trade;
		trade = tradeRepository.save(trade);
		assertThat(trade).isNotNull();
		assertThat(trade).usingRecursiveComparison().isEqualTo(oldTrade);

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		assertThat(listResult).isNotNull();
		assertThat(listResult).hasSize(1);
		assertThat(listResult.get(0)).usingRecursiveComparison().isEqualTo(trade);

		// Delete
		Integer id = trade.getTradeId();
		tradeRepository.delete(trade);
		Optional<Trade> optTrade = tradeRepository.findById(id);
		assertThat(optTrade).isEmpty();
	}
}

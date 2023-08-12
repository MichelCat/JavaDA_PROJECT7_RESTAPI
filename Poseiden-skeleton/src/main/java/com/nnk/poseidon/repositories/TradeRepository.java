package com.nnk.poseidon.repositories;

import com.nnk.poseidon.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * TradeRepository is the interface that manages Trade
 *
 * @author MC
 * @version 1.0
 */
public interface TradeRepository extends JpaRepository<Trade, Integer> {

}

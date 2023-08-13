package com.nnk.poseidon.repository;

import com.nnk.poseidon.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * TradeRepository is the interface that manages Trade
 *
 * @author MC
 * @version 1.0
 */
public interface TradeRepository extends JpaRepository<Trade, Integer> {

}

package com.nnk.poseidon.repository;

import com.nnk.poseidon.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * BidRepository is the interface that manages Bid
 *
 * @author MC
 * @version 1.0
 */
public interface BidRepository extends JpaRepository<Bid, Integer> {

}

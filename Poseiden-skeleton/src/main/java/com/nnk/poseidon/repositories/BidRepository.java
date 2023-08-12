package com.nnk.poseidon.repositories;

import com.nnk.poseidon.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * BidRepository is the interface that manages Bid
 *
 * @author MC
 * @version 1.0
 */
public interface BidRepository extends JpaRepository<Bid, Integer> {

}

package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * BidListRepository is the interface that manages BidList
 *
 * @author MC
 * @version 1.0
 */
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}

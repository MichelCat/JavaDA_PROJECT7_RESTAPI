package com.nnk.poseidon.repository;

import com.nnk.poseidon.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * RuleRepository is the interface that manages Rule
 *
 * @author MC
 * @version 1.0
 */
public interface RuleRepository extends JpaRepository<Rule, Integer> {

}

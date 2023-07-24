package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * RuleNameRepository is the interface that manages RuleName
 *
 * @author MC
 * @version 1.0
 */
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {

}

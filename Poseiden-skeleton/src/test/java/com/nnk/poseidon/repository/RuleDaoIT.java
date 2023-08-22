package com.nnk.poseidon.repository;

import com.nnk.poseidon.data.GlobalData;
import com.nnk.poseidon.data.RuleData;
import com.nnk.poseidon.model.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * RuleDaoIT is the integration test class handling Rule
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RuleDaoIT {

	@Autowired
	private RuleRepository ruleRepository;

	@Test
	@Sql(scripts = GlobalData.scriptClearDataBase)
	void ruleTest() {
		Rule oldRule;
		Rule rule = RuleData.getRuleSource();

		// Save
		oldRule = rule;
		rule = ruleRepository.save(rule);
		assertThat(rule).isNotNull();
		assertThat(rule).usingRecursiveComparison().ignoringFields("id").isEqualTo(oldRule);
		assertThat(rule.getId()).isEqualTo(1);

		// Update
		rule.setName("Rule Name Update");
		oldRule = rule;
		rule = ruleRepository.save(rule);
		assertThat(rule).isNotNull()
							.isEqualTo(oldRule);

		// Find
		List<Rule> listResult = ruleRepository.findAll();
		assertThat(listResult).isNotNull()
								.hasSize(1)
								.contains(rule);

		// Delete
		Integer id = rule.getId();
		ruleRepository.delete(rule);
		Optional<Rule> optRule = ruleRepository.findById(id);
		assertThat(optRule).isEmpty();
	}
}

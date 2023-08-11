package com.nnk.springboot.repositories;

import com.nnk.springboot.data.GlobalData;
import com.nnk.springboot.data.RuleNameData;
import com.nnk.springboot.domain.RuleName;
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
public class RuleDaoIT {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Test
	@Sql(scripts = GlobalData.scriptClearDataBase)
	public void ruleTest() {
		RuleName oldRule;
		RuleName rule = RuleNameData.getRuleNameSource();

		// Save
		oldRule = rule;
		rule = ruleNameRepository.save(rule);
		assertThat(rule).isNotNull();
		assertThat(rule).usingRecursiveComparison().ignoringFields("id").isEqualTo(oldRule);
		assertThat(rule.getId()).isEqualTo(1);

		// Update
		rule.setName("Rule Name Update");
		oldRule = rule;
		rule = ruleNameRepository.save(rule);
		assertThat(rule).isNotNull();
		assertThat(rule).usingRecursiveComparison().isEqualTo(oldRule);

		// Find
		List<RuleName> listResult = ruleNameRepository.findAll();
		assertThat(listResult).isNotNull();
		assertThat(listResult).hasSize(1);
		assertThat(listResult.get(0)).usingRecursiveComparison().isEqualTo(rule);

		// Delete
		Integer id = rule.getId();
		ruleNameRepository.delete(rule);
		Optional<RuleName> optRule = ruleNameRepository.findById(id);
		assertThat(optRule).isEmpty();
	}
}

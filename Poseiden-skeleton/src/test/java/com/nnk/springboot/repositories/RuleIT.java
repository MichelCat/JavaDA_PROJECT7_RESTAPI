package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RuleIT {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Test
	public void ruleTest() {
		RuleName rule = RuleName.builder()
				.name("Rule Name")
				.description("Description")
				.json("Json")
				.template("Template")
				.sqlStr("SQL")
				.sqlPart("SQL Part")
				.build();

		// Save
		rule = ruleNameRepository.save(rule);
//		Assert.assertNotNull(rule.getId());
//		Assert.assertTrue(rule.getName().equals("Rule Name"));
		assertThat(rule).isNotNull();
		assertThat(rule.getId()).isNotNull();
		assertThat(rule.getName()).isEqualTo("Rule Name");

		// Update
		rule.setName("Rule Name Update");
		rule = ruleNameRepository.save(rule);
//		Assert.assertTrue(rule.getName().equals("Rule Name Update"));
		assertThat(rule).isNotNull();
		assertThat(rule.getName()).isEqualTo("Rule Name Update");

		// Find
		List<RuleName> listResult = ruleNameRepository.findAll();
//		Assert.assertTrue(listResult.size() > 0);
		assertThat(listResult).isNotNull();
		assertThat(listResult).hasSize(1);

		// Delete
		Integer id = rule.getId();
		ruleNameRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
//		Assert.assertFalse(ruleList.isPresent());
		assertThat(ruleList).isEmpty();
	}
}

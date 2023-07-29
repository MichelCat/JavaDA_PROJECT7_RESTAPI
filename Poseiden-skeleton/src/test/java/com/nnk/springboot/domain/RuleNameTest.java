package com.nnk.springboot.domain;

import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
public class RuleNameTest {

    @Autowired
    private Validator validator;

    private TestConstraintViolation<Rating> testConstraintViolation;
    private RuleName rule;

    @BeforeEach
    private void setUpBefore() {
        testConstraintViolation = new TestConstraintViolation<>(validator);

        rule = RuleName.builder()
                .name("Rule Name")
                .description("Description")
                .json("Json")
                .template("Template")
                .sqlStr("SQL")
                .sqlPart("SQL Part")
                .build();
    }
}

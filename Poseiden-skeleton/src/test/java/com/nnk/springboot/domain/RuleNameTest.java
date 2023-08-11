package com.nnk.springboot.domain;

import com.nnk.springboot.data.RuleNameData;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * RuleNameTest is the unit test class managing the RuleName
 *
 * @author MC
 * @version 1.0
 */
@SpringBootTest
@ActiveProfiles("test")
public class RuleNameTest {

    @Autowired
    private Validator validator;

    private TestConstraintViolation<RuleName> testConstraintViolation;
    private RuleName rule;

    @BeforeEach
    private void setUpBefore() {
        testConstraintViolation = new TestConstraintViolation<>(validator);

        rule = RuleNameData.getRuleNameSource();
    }

    // -----------------------------------------------------------------------------------------------
    // builder method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void builder_TestBuildAndNew_thenEqual() {
        // GIVEN
        // WHEN
        RuleName objBuild = RuleName.builder()
                                .build();
        RuleName objNew = new RuleName();
        // THEN
        assertThat(objBuild).usingRecursiveComparison().isEqualTo(objNew);
    }

    // -----------------------------------------------------------------------------------------------
    // name attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void name_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setName("Rule Name");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rule, errorList);
        assertThat(rule.getName()).isEqualTo("Rule Name");
    }

    @Test
    public void name_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setName(" ");
        // THEN
        String[][] errorList = {{"name", "Name is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void name_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setName("");
        // THEN
        String[][] errorList = {{"name", "Name is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void name_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setName(null);
        // THEN
        String[][] errorList = {{"name", "Name is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void name_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setName(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"name", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(rule, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // description attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void description_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setDescription("Description");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rule, errorList);
        assertThat(rule.getDescription()).isEqualTo("Description");
    }

    @Test
    public void description_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setDescription(" ");
        // THEN
        String[][] errorList = {{"description", "Description is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void description_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setDescription("");
        // THEN
        String[][] errorList = {{"description", "Description is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void description_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setDescription(null);
        // THEN
        String[][] errorList = {{"description", "Description is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void description_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setDescription(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"description", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(rule, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // json attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void json_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setJson("Json Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rule, errorList);
        assertThat(rule.getJson()).isEqualTo("Json Test");
    }

    @Test
    public void json_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setJson(" ");
        // THEN
        String[][] errorList = {{"json", "Json is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void json_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setJson("");
        // THEN
        String[][] errorList = {{"json", "Json is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void json_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setJson(null);
        // THEN
        String[][] errorList = {{"json", "Json is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void json_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setJson(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"json", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(rule, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // template attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void template_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setTemplate("Template");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rule, errorList);
        assertThat(rule.getTemplate()).isEqualTo("Template");
    }

    @Test
    public void template_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setTemplate(" ");
        // THEN
        String[][] errorList = {{"template", "Template is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void template_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setTemplate("");
        // THEN
        String[][] errorList = {{"template", "Template is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void template_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setTemplate(null);
        // THEN
        String[][] errorList = {{"template", "Template is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void template_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setTemplate(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"template", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(rule, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // sqlStr attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void sqlStr_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setSqlStr("SQL");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rule, errorList);
        assertThat(rule.getSqlStr()).isEqualTo("SQL");
    }

    @Test
    public void sqlStr_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setSqlStr(" ");
        // THEN
        String[][] errorList = {{"sqlStr", "SqlStr is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void sqlStr_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setSqlStr("");
        // THEN
        String[][] errorList = {{"sqlStr", "SqlStr is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void sqlStr_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setSqlStr(null);
        // THEN
        String[][] errorList = {{"sqlStr", "SqlStr is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void sqlStr_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setSqlStr(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"sqlStr", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(rule, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // sqlPart attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    public void sqlPart_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setSqlPart("SQL Part");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rule, errorList);
        assertThat(rule.getSqlPart()).isEqualTo("SQL Part");
    }

    @Test
    public void sqlPart_blank_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setSqlPart(" ");
        // THEN
        String[][] errorList = {{"sqlPart", "SqlPart is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void sqlPart_empty_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setSqlPart("");
        // THEN
        String[][] errorList = {{"sqlPart", "SqlPart is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void sqlPart_null_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setSqlPart(null);
        // THEN
        String[][] errorList = {{"sqlPart", "SqlPart is mandatory"}};
        testConstraintViolation.checking(rule, errorList);
    }

    @Test
    public void sqlPart_sizeTooBig_thenOneConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setSqlPart(StringUtils.repeat('a', 126));
        // THEN
        String[][] errorList = {{"sqlPart", "Maximum length of 125 characters"}};
        testConstraintViolation.checking(rule, errorList);
    }
}

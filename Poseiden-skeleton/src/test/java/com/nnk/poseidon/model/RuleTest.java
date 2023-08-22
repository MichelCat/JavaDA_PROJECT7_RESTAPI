package com.nnk.poseidon.model;

import com.nnk.poseidon.data.RuleData;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * RuleTest is the unit test class managing the Rule
 *
 * @author MC
 * @version 1.0
 */
@SpringBootTest
@ActiveProfiles("test")
class RuleTest {

    @Autowired
    private Validator validator;

    private TestConstraintViolation<Rule> testConstraintViolation;
    private Rule rule;

    @BeforeEach
    private void setUpBefore() {
        testConstraintViolation = new TestConstraintViolation<>(validator);

        rule = RuleData.getRuleSource();
    }

    // -----------------------------------------------------------------------------------------------
    // builder method
    // -----------------------------------------------------------------------------------------------
    @Test
    void builder_TestBuildAndNew_thenEqual() {
        // GIVEN
        // WHEN
        Rule objBuild = Rule.builder()
                                .build();
        Rule objNew = new Rule();
        // THEN
        assertThat(objBuild).isEqualTo(objNew);
    }

    // -----------------------------------------------------------------------------------------------
    // name attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void name_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setName("Rule Name");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rule, errorList);
        assertThat(rule.getName()).isEqualTo("Rule Name");
    }

    private static Stream<Arguments> listOfNameToTest() {
        String[][] errorSpace = {{"name", "{constraint.notBlank.rule.name}"}};
        String[][] errorEmpty = {{"name", "{constraint.notBlank.rule.name}"}};
        String[][] errorNull = {{"name", "{constraint.notBlank.rule.name}"}};
        String[][] errorSizeTooBig = {{"name", "{constraint.size.global}"}};

        return Stream.of(
                Arguments.of(" ", errorSpace, "space")
                , Arguments.of("", errorEmpty, "empty")
                , Arguments.of(null, errorNull, "null")
                , Arguments.of(StringUtils.repeat('a', 126), errorSizeTooBig, "size too big")
        );
    }

    @ParameterizedTest(name = "Name is {2} ({0}).")
    @MethodSource("listOfNameToTest")
    void name_thenConstraintViolation(String name, String[][] errorList, String message) {
        // GIVEN
        // WHEN
        rule.setName(name);
        // THEN
        testConstraintViolation.checking(rule, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // description attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void description_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setDescription("Description");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rule, errorList);
        assertThat(rule.getDescription()).isEqualTo("Description");
    }

    private static Stream<Arguments> listOfDescriptionToTest() {
        String[][] errorSpace = {{"description", "{constraint.notBlank.rule.description}"}};
        String[][] errorEmpty = {{"description", "{constraint.notBlank.rule.description}"}};
        String[][] errorNull = {{"description", "{constraint.notBlank.rule.description}"}};
        String[][] errorSizeTooBig = {{"description", "{constraint.size.global}"}};

        return Stream.of(
                Arguments.of(" ", errorSpace, "space")
                , Arguments.of("", errorEmpty, "empty")
                , Arguments.of(null, errorNull, "null")
                , Arguments.of(StringUtils.repeat('a', 126), errorSizeTooBig, "size too big")
        );
    }

    @ParameterizedTest(name = "Description is {2} ({0}).")
    @MethodSource("listOfDescriptionToTest")
    void description_thenConstraintViolation(String description, String[][] errorList, String message) {
        // GIVEN
        // WHEN
        rule.setDescription(description);
        // THEN
        testConstraintViolation.checking(rule, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // json attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void json_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setJson("Json Test");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rule, errorList);
        assertThat(rule.getJson()).isEqualTo("Json Test");
    }

    private static Stream<Arguments> listOfJsonToTest() {
        String[][] errorSpace = {{"json", "{constraint.notBlank.rule.json}"}};
        String[][] errorEmpty = {{"json", "{constraint.notBlank.rule.json}"}};
        String[][] errorNull = {{"json", "{constraint.notBlank.rule.json}"}};
        String[][] errorSizeTooBig = {{"json", "{constraint.size.global}"}};

        return Stream.of(
                Arguments.of(" ", errorSpace, "space")
                , Arguments.of("", errorEmpty, "empty")
                , Arguments.of(null, errorNull, "null")
                , Arguments.of(StringUtils.repeat('a', 126), errorSizeTooBig, "size too big")
        );
    }

    @ParameterizedTest(name = "Json is {2} ({0}).")
    @MethodSource("listOfJsonToTest")
    void json_thenConstraintViolation(String json, String[][] errorList, String message) {
        // GIVEN
        // WHEN
        rule.setJson(json);
        // THEN
        testConstraintViolation.checking(rule, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // template attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void template_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setTemplate("Template");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rule, errorList);
        assertThat(rule.getTemplate()).isEqualTo("Template");
    }

    private static Stream<Arguments> listOfTemplateToTest() {
        String[][] errorSpace = {{"template", "{constraint.notBlank.rule.template}"}};
        String[][] errorEmpty = {{"template", "{constraint.notBlank.rule.template}"}};
        String[][] errorNull = {{"template", "{constraint.notBlank.rule.template}"}};
        String[][] errorSizeTooBig = {{"template", "{constraint.size.global}"}};

        return Stream.of(
                Arguments.of(" ", errorSpace, "space")
                , Arguments.of("", errorEmpty, "empty")
                , Arguments.of(null, errorNull, "null")
                , Arguments.of(StringUtils.repeat('a', 126), errorSizeTooBig, "size too big")
        );
    }

    @ParameterizedTest(name = "Template is {2} ({0}).")
    @MethodSource("listOfTemplateToTest")
    void template_thenConstraintViolation(String template, String[][] errorList, String message) {
        // GIVEN
        // WHEN
        rule.setTemplate(template);
        // THEN
        testConstraintViolation.checking(rule, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // sqlStr attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void sqlStr_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setSqlStr("SQL");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rule, errorList);
        assertThat(rule.getSqlStr()).isEqualTo("SQL");
    }

    private static Stream<Arguments> listOfSqlStrToTest() {
        String[][] errorSpace = {{"sqlStr", "{constraint.notBlank.rule.sqlStr}"}};
        String[][] errorEmpty = {{"sqlStr", "{constraint.notBlank.rule.sqlStr}"}};
        String[][] errorNull = {{"sqlStr", "{constraint.notBlank.rule.sqlStr}"}};
        String[][] errorSizeTooBig = {{"sqlStr", "{constraint.size.global}"}};

        return Stream.of(
                Arguments.of(" ", errorSpace, "space")
                , Arguments.of("", errorEmpty, "empty")
                , Arguments.of(null, errorNull, "null")
                , Arguments.of(StringUtils.repeat('a', 126), errorSizeTooBig, "size too big")
        );
    }

    @ParameterizedTest(name = "SqlStr is {2} ({0}).")
    @MethodSource("listOfSqlStrToTest")
    void sqlStr_thenConstraintViolation(String sqlStr, String[][] errorList, String message) {
        // GIVEN
        // WHEN
        rule.setSqlStr(sqlStr);
        // THEN
        testConstraintViolation.checking(rule, errorList);
    }

    // -----------------------------------------------------------------------------------------------
    // sqlPart attribute
    // -----------------------------------------------------------------------------------------------
    @Test
    void sqlPart_normal_thenNoConstraintViolation() {
        // GIVEN
        // WHEN
        rule.setSqlPart("SQL Part");
        // THEN
        String[][] errorList = {};
        testConstraintViolation.checking(rule, errorList);
        assertThat(rule.getSqlPart()).isEqualTo("SQL Part");
    }

    private static Stream<Arguments> listOfSqlPartToTest() {
        String[][] errorSpace = {{"sqlPart", "{constraint.notBlank.rule.sqlPart}"}};
        String[][] errorEmpty = {{"sqlPart", "{constraint.notBlank.rule.sqlPart}"}};
        String[][] errorNull = {{"sqlPart", "{constraint.notBlank.rule.sqlPart}"}};
        String[][] errorSizeTooBig = {{"sqlPart", "{constraint.size.global}"}};

        return Stream.of(
                Arguments.of(" ", errorSpace, "space")
                , Arguments.of("", errorEmpty, "empty")
                , Arguments.of(null, errorNull, "null")
                , Arguments.of(StringUtils.repeat('a', 126), errorSizeTooBig, "size too big")
        );
    }

    @ParameterizedTest(name = "SqlPart is {2} ({0}).")
    @MethodSource("listOfSqlPartToTest")
    void sqlPart_thenConstraintViolation(String sqlPart, String[][] errorList, String message) {
        // GIVEN
        // WHEN
        rule.setSqlPart(sqlPart);
        // THEN
        testConstraintViolation.checking(rule, errorList);
    }
}

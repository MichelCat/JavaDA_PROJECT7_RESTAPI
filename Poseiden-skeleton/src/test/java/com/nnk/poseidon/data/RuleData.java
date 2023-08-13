package com.nnk.poseidon.data;

import com.nnk.poseidon.model.Rule;
import com.nnk.poseidon.mapper.MultiValueMapMapper;
import org.springframework.util.MultiValueMap;

/**
 * RuleData is the class containing the Rule test data
 *
 * @author MC
 * @version 1.0
 */
public class RuleData {

    public static Rule getRuleSource() {
        return Rule.builder()
                .name("Rule Name")
                .description("Description")
                .json("Json")
                .template("Template")
                .sqlStr("SQL")
                .sqlPart("SQL Part")
                .build();
    }

    public static Rule getRuleSave() {
        Rule rule = getRuleSource();
        rule.setId(1);
        return rule;
    }

    public static MultiValueMap<String, String> getRuleSourceController() {
        Rule rule = Rule.builder()
                .name("Rule Name")
                .description("Description")
                .json("Json")
                .template("Template")
                .sqlStr("SQL")
                .sqlPart("SQL Part")
                .build();
        return MultiValueMapMapper.convert(rule);
    }

    public static MultiValueMap<String, String> getRuleSaveController() {
        Rule rule = Rule.builder()
                .id(1)
                .name("Rule Name")
                .description("Description")
                .json("Json")
                .template("Template")
                .sqlStr("SQL")
                .sqlPart("SQL Part")
                .build();
        return MultiValueMapMapper.convert(rule);
    }

    public final static String scriptCreateRule = "/data/createRule.sql";
}

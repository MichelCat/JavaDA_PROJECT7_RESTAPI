package com.nnk.springboot.data;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.mapper.MultiValueMapMapper;
import org.springframework.util.MultiValueMap;

/**
 * RuleNameData is the class containing the Rule Name test data
 *
 * @author MC
 * @version 1.0
 */
public class RuleNameData {

    public static RuleName getRuleNameSource() {
        return RuleName.builder()
                .name("Rule Name")
                .description("Description")
                .json("Json")
                .template("Template")
                .sqlStr("SQL")
                .sqlPart("SQL Part")
                .build();
    }

    public static RuleName getRuleNameSave() {
        RuleName ruleName = getRuleNameSource();
        ruleName.setId(1);
        return ruleName;
    }

    public static MultiValueMap<String, String> getRuleNameSourceController() {
        RuleName ruleName = RuleName.builder()
                .name("Rule Name")
                .description("Description")
                .json("Json")
                .template("Template")
                .sqlStr("SQL")
                .sqlPart("SQL Part")
                .build();
        return MultiValueMapMapper.convert(ruleName);
    }

    public static MultiValueMap<String, String> getRuleNameSaveController() {
        RuleName ruleName = RuleName.builder()
                .id(1)
                .name("Rule Name")
                .description("Description")
                .json("Json")
                .template("Template")
                .sqlStr("SQL")
                .sqlPart("SQL Part")
                .build();
        return MultiValueMapMapper.convert(ruleName);
    }

    public final static String scriptCreateRuleName = "/data/createRuleName.sql";
}

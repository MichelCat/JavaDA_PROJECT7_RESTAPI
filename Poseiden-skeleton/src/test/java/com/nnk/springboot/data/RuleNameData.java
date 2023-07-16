package com.nnk.springboot.data;

import com.nnk.springboot.domain.RuleName;

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
}

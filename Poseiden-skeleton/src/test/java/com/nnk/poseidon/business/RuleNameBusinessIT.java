package com.nnk.poseidon.business;

import com.nnk.poseidon.data.RuleData;
import com.nnk.poseidon.domain.Rule;
import com.nnk.poseidon.exception.MyExceptionBadRequestException;
import com.nnk.poseidon.exception.MyExceptionNotFoundException;
import com.nnk.poseidon.data.GlobalData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * RuleNameBusinessIT is a class of integration tests on Rules service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class RuleNameBusinessIT {

    @Autowired
    private RuleNameBusiness ruleNameBusiness;

    private Rule ruleSource;
    private Rule ruleSave;


    @BeforeEach
    public void setUpBefore() {
        ruleSource = RuleData.getRuleSource();
        ruleSave = RuleData.getRuleSave();
    }

    // -----------------------------------------------------------------------------------------------
    // getRulesList method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    public void getRulesList_findAllNormal() {
        // GIVEN
        // WHEN
        List<Rule> result = ruleNameBusiness.getRulesList();
        // THEN
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(ruleSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void getRulesList_findAllEmpty() {
        // GIVEN
        // WHEN
        List<Rule> result = ruleNameBusiness.getRulesList();
        // THEN
        assertThat(result.size()).isEqualTo(0);
    }

    // -----------------------------------------------------------------------------------------------
    // createRule method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void createRule_ruleNotExist() {
        // GIVEN
        // WHEN
        Rule result = ruleNameBusiness.createRule(ruleSource);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("creationDate")
                .isEqualTo(ruleSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    public void createRule_ruleExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> ruleNameBusiness.createRule(ruleSave));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // getRuleById method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    public void getRuleById_ruleExist() {
        // GIVEN
        // WHEN
        assertThat(ruleNameBusiness.getRuleById(ruleSave.getId())).isEqualTo(ruleSave);
        // THEN
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void getRuleById_ruleNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.getRuleById(ruleSave.getId()));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // updateRule method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    public void updateRule_ruleExist() {
        // GIVEN
        // WHEN
        Rule result = ruleNameBusiness.updateRule(ruleSave.getId(), ruleSave);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("asOfDate")
                .isEqualTo(ruleSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void updateRule_ruleNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.updateRule(ruleSave.getId(), ruleSave));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRule method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleData.scriptCreateRule)
    public void deleteRule_ruleExist() {
        // GIVEN
        // WHEN
        ruleNameBusiness.deleteRule(ruleSave.getId());
        // THEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.getRuleById(ruleSave.getId()));
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void deleteRule_ruleNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.deleteRule(ruleSave.getId()));
        // THEN
    }
}

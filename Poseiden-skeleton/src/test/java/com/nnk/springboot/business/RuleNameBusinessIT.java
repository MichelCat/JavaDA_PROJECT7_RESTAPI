package com.nnk.springboot.business;

import com.nnk.springboot.data.RuleNameData;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exception.MyExceptionBadRequestException;
import com.nnk.springboot.exception.MyExceptionNotFoundException;
import com.nnk.springboot.data.GlobalData;
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
 * RuleNameBusinessIT is a class of integration tests on Rule Names service.
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

    private RuleName ruleNameSource;
    private RuleName ruleNameSave;


    @BeforeEach
    public void setUpBefore() {
        ruleNameSource = RuleNameData.getRuleNameSource();
        ruleNameSave = RuleNameData.getRuleNameSave();
    }

    // -----------------------------------------------------------------------------------------------
    // getRuleNamesList method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleNameData.scriptCreateRuleName)
    public void getRuleNamesList_findAllNormal() {
        // GIVEN
        // WHEN
        List<RuleName> result = ruleNameBusiness.getRuleNamesList();
        // THEN
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(ruleNameSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void getRuleNamesList_findAllEmpty() {
        // GIVEN
        // WHEN
        List<RuleName> result = ruleNameBusiness.getRuleNamesList();
        // THEN
        assertThat(result.size()).isEqualTo(0);
    }

    // -----------------------------------------------------------------------------------------------
    // createRuleName method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void createRuleName_ruleNameNotExist() {
        // GIVEN
        // WHEN
        RuleName result = ruleNameBusiness.createRuleName(ruleNameSource);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("creationDate")
                .isEqualTo(ruleNameSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleNameData.scriptCreateRuleName)
    public void createRuleName_ruleNameExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> ruleNameBusiness.createRuleName(ruleNameSave));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // getRuleNameById method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleNameData.scriptCreateRuleName)
    public void getRuleNameById_ruleNameExist() {
        // GIVEN
        // WHEN
        assertThat(ruleNameBusiness.getRuleNameById(ruleNameSave.getId())).isEqualTo(ruleNameSave);
        // THEN
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void getRuleNameById_ruleNameNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.getRuleNameById(ruleNameSave.getId()));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // updateRuleName method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleNameData.scriptCreateRuleName)
    public void updateRuleName_ruleNameExist() {
        // GIVEN
        // WHEN
        RuleName result = ruleNameBusiness.updateRuleName(ruleNameSave.getId(), ruleNameSave);
        // THEN
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("asOfDate")
                .isEqualTo(ruleNameSave);
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void updateRuleName_ruleNameNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.updateRuleName(ruleNameSave.getId(), ruleNameSave));
        // THEN
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRuleName method
    // -----------------------------------------------------------------------------------------------
    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    @Sql(scripts = RuleNameData.scriptCreateRuleName)
    public void deleteRuleName_ruleNameExist() {
        // GIVEN
        // WHEN
        ruleNameBusiness.deleteRuleName(ruleNameSave.getId());
        // THEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.getRuleNameById(ruleNameSave.getId()));
    }

    @Test
    @Sql(scripts = GlobalData.scriptClearDataBase)
    public void deleteRuleName_ruleNameNotExist() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.deleteRuleName(ruleNameSave.getId()));
        // THEN
    }
}

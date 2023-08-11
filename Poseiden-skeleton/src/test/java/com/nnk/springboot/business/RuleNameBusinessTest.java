package com.nnk.springboot.business;

import com.nnk.springboot.data.RuleNameData;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exception.MyExceptionBadRequestException;
import com.nnk.springboot.exception.MyExceptionNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * RuleNameBusinessTest is a unit testing class of the Rule Name service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class RuleNameBusinessTest {

    @Autowired
    private RuleNameBusiness ruleNameBusiness;

    @MockBean
    private RuleNameRepository ruleNameRepository;

    private RuleName ruleNameSource;
    private RuleName ruleNameSave;
    public List<RuleName> ruleNameList;


    @BeforeEach
    public void setUpBefore() {
        ruleNameSource = RuleNameData.getRuleNameSource();
        ruleNameSave = RuleNameData.getRuleNameSave();

        ruleNameList = new ArrayList<>();
        ruleNameList.add(ruleNameSave);
    }

    // -----------------------------------------------------------------------------------------------
    // getRuleNamesList method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void getRuleNamesList_findAllNormal() {
        // GIVEN
        when(ruleNameRepository.findAll()).thenReturn(ruleNameList);
        // WHEN
        assertThat(ruleNameBusiness.getRuleNamesList()).isEqualTo(ruleNameList);
        // THEN
        verify(ruleNameRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getRuleNamesList_findAllEmpty() {
        // GIVEN
        when(ruleNameRepository.findAll()).thenReturn(new ArrayList<>());
        // WHEN
        assertThat(ruleNameBusiness.getRuleNamesList()).isEmpty();
        // THEN
        verify(ruleNameRepository, Mockito.times(1)).findAll();
    }

    // -----------------------------------------------------------------------------------------------
    // createRuleName method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void createRuleName_saveNormal() {
        // GIVEN
        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(ruleNameRepository.save(ruleNameSource)).thenReturn(ruleNameSave);
        // WHEN
        assertThat(ruleNameBusiness.createRuleName(ruleNameSource)).isEqualTo(ruleNameSave);
        // THEN
        verify(ruleNameRepository, Mockito.times(1)).save(any(RuleName.class));
    }

    @Test
    public void createRuleName_nullRuleNameParameter_returnNullPointer() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> ruleNameBusiness.createRuleName(null));
        // THEN
        verify(ruleNameRepository, Mockito.times(0)).save(any(RuleName.class));
    }

    @Test
    public void createRuleName_RuleNameExist_returnBadRequest() {
        // GIVEN
        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.of(ruleNameSave));
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> ruleNameBusiness.createRuleName(ruleNameSave));
        // THEN
        verify(ruleNameRepository, Mockito.times(0)).save(any(RuleName.class));
    }

    // -----------------------------------------------------------------------------------------------
    // getRuleNameById method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void getRuleNameById_findByIdNormal() {
        // GIVEN
        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.of(ruleNameSave));
        // WHEN
        assertThat(ruleNameBusiness.getRuleNameById(1)).isEqualTo(ruleNameSave);
        // THEN
        verify(ruleNameRepository, Mockito.times(1)).findById(any(Integer.class));
    }

    @Test
    public void getRuleNameById_nullIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.getRuleNameById(null));
        // THEN
        verify(ruleNameRepository, Mockito.times(1)).findById(null);
    }

    // -----------------------------------------------------------------------------------------------
    // updateRuleName method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void updateRuleName_updateNormal() {
        // GIVEN
        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.of(ruleNameSave));
        when(ruleNameRepository.save(ruleNameSave)).thenReturn(ruleNameSave);
        // WHEN
        assertThat(ruleNameBusiness.updateRuleName(1, ruleNameSource)).isEqualTo(ruleNameSave);
        // THEN
        verify(ruleNameRepository, Mockito.times(1)).save(any(RuleName.class));
    }

    @Test
    public void updateRuleName_RuleNameNotExist_returnNotFound() {
        // GIVEN
        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.updateRuleName(2, ruleNameSource));
        // THEN
        verify(ruleNameRepository, Mockito.times(0)).save(any(RuleName.class));
    }

    @Test
    public void updateRuleName_nullIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.updateRuleName(null, ruleNameSource));
        // THEN
        verify(ruleNameRepository, Mockito.times(0)).save(any(RuleName.class));
    }

    @Test
    public void updateRuleName_zeroIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.updateRuleName(0, ruleNameSource));
        // THEN
        verify(ruleNameRepository, Mockito.times(0)).save(any(RuleName.class));
    }

    @Test
    public void updateRuleName_nullRuleNameParameter_returnNotFound() {
        // GIVEN
        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.of(ruleNameSave));
        // WHEN
        assertThrows(MyExceptionBadRequestException.class, () -> ruleNameBusiness.updateRuleName(1, null));
        // THEN
        verify(ruleNameRepository, Mockito.times(0)).save(any(RuleName.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRuleName method
    // -----------------------------------------------------------------------------------------------
    @Test
    public void deleteRuleName_deleteNormal() {
        // GIVEN
        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.of(ruleNameSave));
        doNothing().when(ruleNameRepository).deleteById(any(Integer.class));
        // WHEN
        ruleNameBusiness.deleteRuleName(1);
        // THEN
        verify(ruleNameRepository, Mockito.times(1)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteRuleName_RuleNameNotExist_returnNotFound() {
        // GIVEN
        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.deleteRuleName(2));
        // THEN
        verify(ruleNameRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteRuleName_nullIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.deleteRuleName(null));
        // THEN
        verify(ruleNameRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    public void deleteRuleName_zeroIdParameter_returnNotFound() {
        // GIVEN
        // WHEN
        assertThrows(MyExceptionNotFoundException.class, () -> ruleNameBusiness.deleteRuleName(0));
        // THEN
        verify(ruleNameRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }
}

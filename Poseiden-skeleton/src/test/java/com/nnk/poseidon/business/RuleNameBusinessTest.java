package com.nnk.poseidon.business;

import com.nnk.poseidon.data.RuleData;
import com.nnk.poseidon.model.Rule;
import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.repository.RuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
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
 * RuleNameBusinessTest is a unit testing class of the Rule service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class RuleNameBusinessTest {

    @Autowired
    private RuleNameBusiness ruleNameBusiness;
    @Autowired
    private MessageSource messageSource;

    @MockBean
    private RuleRepository ruleRepository;

    private TestMessageSource testMessageSource;
    private Rule ruleSource;
    private Rule ruleSave;
    public List<Rule> ruleList;


    @BeforeEach
    public void setUpBefore() {
        testMessageSource = new TestMessageSource(messageSource);

        ruleSource = RuleData.getRuleSource();
        ruleSave = RuleData.getRuleSave();

        ruleList = new ArrayList<>();
        ruleList.add(ruleSave);
    }

    // -----------------------------------------------------------------------------------------------
    // getRulesList method
    // -----------------------------------------------------------------------------------------------
    @Test
    void getRulesList_findAllNormal() {
        // GIVEN
        when(ruleRepository.findAll()).thenReturn(ruleList);
        // WHEN
        assertThat(ruleNameBusiness.getRulesList()).isEqualTo(ruleList);
        // THEN
        verify(ruleRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getRulesList_findAllEmpty() {
        // GIVEN
        when(ruleRepository.findAll()).thenReturn(new ArrayList<>());
        // WHEN
        assertThat(ruleNameBusiness.getRulesList()).isEmpty();
        // THEN
        verify(ruleRepository, Mockito.times(1)).findAll();
    }

    // -----------------------------------------------------------------------------------------------
    // createRule method
    // -----------------------------------------------------------------------------------------------
    @Test
    void createRule_saveNormal() throws MyException {
        // GIVEN
        when(ruleRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(ruleRepository.save(ruleSource)).thenReturn(ruleSave);
        // WHEN
        assertThat(ruleNameBusiness.createRule(ruleSource)).isEqualTo(ruleSave);
        // THEN
        verify(ruleRepository, Mockito.times(1)).save(any(Rule.class));
    }

    @Test
    void createRule_nullRuleParameter_returnNullPointer() {
        // GIVEN
        when(ruleRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ruleNameBusiness.createRule(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rule.nullRule", null);
        verify(ruleRepository, Mockito.times(0)).save(any(Rule.class));
    }

    @Test
    void createRule_ruleExist_returnMyException() {
        // GIVEN
        when(ruleRepository.findById(any(Integer.class))).thenReturn(Optional.of(ruleSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ruleNameBusiness.createRule(ruleSave));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rule.ruleExists", new Object[] { ruleSave.getId() });
        verify(ruleRepository, Mockito.times(0)).save(any(Rule.class));
    }

    // -----------------------------------------------------------------------------------------------
    // getRuleById method
    // -----------------------------------------------------------------------------------------------
    @Test
    void getRuleById_findByIdNormal() throws MyException {
        // GIVEN
        when(ruleRepository.findById(any(Integer.class))).thenReturn(Optional.of(ruleSave));
        // WHEN
        assertThat(ruleNameBusiness.getRuleById(1)).isEqualTo(ruleSave);
        // THEN
        verify(ruleRepository, Mockito.times(1)).findById(any(Integer.class));
    }

    @Test
    void getRuleById_nullIdParameter_returnMyException() {
        // GIVEN
        when(ruleRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ruleNameBusiness.getRuleById(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rule.unknown", new Object[] { null });
        verify(ruleRepository, Mockito.times(1)).findById(null);
    }

    // -----------------------------------------------------------------------------------------------
    // updateRule method
    // -----------------------------------------------------------------------------------------------
    @Test
    void updateRule_updateNormal() throws MyException {
        // GIVEN
        when(ruleRepository.findById(any(Integer.class))).thenReturn(Optional.of(ruleSave));
        when(ruleRepository.save(ruleSave)).thenReturn(ruleSave);
        // WHEN
        assertThat(ruleNameBusiness.updateRule(1, ruleSource)).isEqualTo(ruleSave);
        // THEN
        verify(ruleRepository, Mockito.times(1)).save(any(Rule.class));
    }

    @Test
    void updateRule_RuleNotExist_returnMyException() {
        // GIVEN
        when(ruleRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ruleNameBusiness.updateRule(2, ruleSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rule.unknown", new Object[] { 2 });
        verify(ruleRepository, Mockito.times(0)).save(any(Rule.class));
    }

    @Test
    void updateRule_nullIdParameter_returnMyException() {
        // GIVEN
        when(ruleRepository.findById(null)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ruleNameBusiness.updateRule(null, ruleSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rule.unknown", new Object[] { null });
        verify(ruleRepository, Mockito.times(0)).save(any(Rule.class));
    }

    @Test
    void updateRule_zeroIdParameter_returnMyException() {
        // GIVEN
        when(ruleRepository.findById(0)).thenReturn(Optional.empty());
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ruleNameBusiness.updateRule(0, ruleSource));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rule.unknown",  new Object[] { 0 });
        verify(ruleRepository, Mockito.times(0)).save(any(Rule.class));
    }

    @Test
    void updateRule_nullRuleParameter_returnMyException() {
        // GIVEN
        when(ruleRepository.findById(any(Integer.class))).thenReturn(Optional.of(ruleSave));
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ruleNameBusiness.updateRule(1, null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rule.nullRule", null);
        verify(ruleRepository, Mockito.times(0)).save(any(Rule.class));
    }

    // -----------------------------------------------------------------------------------------------
    // deleteRule method
    // -----------------------------------------------------------------------------------------------
    @Test
    void deleteRule_deleteNormal() throws MyException {
        // GIVEN
        when(ruleRepository.existsById(any(Integer.class))).thenReturn(true);
        doNothing().when(ruleRepository).deleteById(any(Integer.class));
        // WHEN
        ruleNameBusiness.deleteRule(1);
        // THEN
        verify(ruleRepository, Mockito.times(1)).deleteById(any(Integer.class));
    }

    @Test
    void deleteRule_RuleNotExist_returnMyException() {
        // GIVEN
        when(ruleRepository.existsById(any(Integer.class))).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ruleNameBusiness.deleteRule(2));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rule.unknown", new Object[] { 2 });
        verify(ruleRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    void deleteRule_nullIdParameter_returnMyException() {
        // GIVEN
        when(ruleRepository.existsById(null)).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ruleNameBusiness.deleteRule(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rule.unknown", new Object[] { null });
        verify(ruleRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }

    @Test
    void deleteRule_zeroIdParameter_returnMyException() {
        // GIVEN
        when(ruleRepository.existsById(0)).thenReturn(false);
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> ruleNameBusiness.deleteRule(0));
        // THEN
        testMessageSource.compare(exception
                            , "exception.rule.unknown", new Object[] { 0 });
        verify(ruleRepository, Mockito.times(0)).deleteById(any(Integer.class));
    }
}

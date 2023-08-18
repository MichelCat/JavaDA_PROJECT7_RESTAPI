package com.nnk.poseidon.business;

import com.nnk.poseidon.model.Rule;
import com.nnk.poseidon.exception.MyExceptionBadRequestException;
import com.nnk.poseidon.exception.MyExceptionNotFoundException;
import com.nnk.poseidon.repository.RuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * RuleNameBusiness is the Rules page processing service
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Service
public class RuleNameBusiness {

    @Autowired
    private RuleRepository ruleRepository;

    /**
     * Find list of Rules
     *
     * @return List of Rules
     */
    public List<Rule> getRulesList() {
        return ruleRepository.findAll();
    }

    /**
     * Create new Rule
     *
     * @param rule The Rule object added
     *
     * @return Rule added
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public Rule createRule(final Rule rule)
            throws MyExceptionBadRequestException {
        // Rule parameter is null
        if (rule == null) {
            log.debug("THROW, Rule is null.");
            throw new MyExceptionBadRequestException("throw.rule.nullRule");
        }
        // Rule exist
        Integer id = rule.getId();
        if (id != null) {
            Optional<Rule> optRuleEntity = ruleRepository.findById(id);
            if (optRuleEntity.isPresent()) {
                log.debug("THROW, Rule exist ({}).", optRuleEntity.get());
                throw new MyExceptionBadRequestException("throw.rule.ruleExists");
            }
        }
        // Rule saved
        return ruleRepository.save(rule);
    }

    /**
     * Find Rule
     *
     * @param id Rule ID founded
     *
     * @return Rule founded
     * @throws MyExceptionNotFoundException Exception not found
     */
    public Rule getRuleById(final Integer id)
            throws MyExceptionNotFoundException {
        // Rule does not exist
        Optional<Rule> optRuleEntity = ruleRepository.findById(id);
        if (optRuleEntity.isPresent() == false) {
            log.debug("THROW, Rule not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.rule.unknown", id);
        }
        // Rule found
        return optRuleEntity.get();
    }

    /**
     * Updated Rule
     *
     * @param id Rule ID updated
     * @param rule The Rule object updated
     *
     * @return Rule updated
     * @throws MyExceptionNotFoundException Exception not found
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public Rule updateRule(final Integer id
            , final Rule rule)
            throws MyExceptionNotFoundException {
        // Rule does not exist
        Optional<Rule> optRuleEntity = ruleRepository.findById(id);
        if (optRuleEntity.isPresent() == false) {
            log.debug("THROW, Rule not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.rule.unknown", id);
        }
        // Rule parameter is null
        if (rule == null) {
            log.debug("THROW, Rule is null.");
            throw new MyExceptionBadRequestException("throw.rule.nullRule");
        }
        // Rule updated
        Rule ruleEntity = optRuleEntity.get();
        ruleEntity.setName(rule.getName());
        ruleEntity.setDescription(rule.getDescription());
        ruleEntity.setJson(rule.getJson());
        ruleEntity.setTemplate(rule.getTemplate());
        ruleEntity.setSqlStr(rule.getSqlStr());
        ruleEntity.setSqlPart(rule.getSqlPart());
        return ruleRepository.save(ruleEntity);
    }

    /**
     * Deleted Rule
     *
     * @param id Rule ID deleted
     *
     * @return void
     * @throws MyExceptionNotFoundException Exception not found
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteRule(final Integer id)
            throws MyExceptionNotFoundException {
        // Rule does not exist
        if (ruleRepository.existsById(id) == false) {
            log.debug("THROW, Rule not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.rule.unknown", id);
        }
        // Rule deleted
        ruleRepository.deleteById(id);
    }
}

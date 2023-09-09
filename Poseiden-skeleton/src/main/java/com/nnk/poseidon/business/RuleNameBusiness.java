package com.nnk.poseidon.business;

import com.nnk.poseidon.model.Rule;
import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.repository.RuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    @Autowired
    private MessageSource messageSource;

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
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Rule createRule(final Rule rule)
            throws MyException {
        // Rule parameter is null
        if (rule == null) {
            String msgSource = messageSource.getMessage("exception.rule.nullRule"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Rule exist
        Integer id = rule.getId();
        if (id != null) {
            Optional<Rule> optRuleEntity = ruleRepository.findById(id);
            if (optRuleEntity.isPresent()) {
                String msgSource = messageSource.getMessage("exception.rule.ruleExists"
                                    , new Object[] { id }
                                    , LocaleContextHolder.getLocale());
                log.debug("Exception, " + msgSource);
                throw new MyException(msgSource);
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
     * @throws MyException Exception
     */
    public Rule getRuleById(final Integer id)
            throws MyException {
        // Rule does not exist
        Optional<Rule> optRuleEntity = ruleRepository.findById(id);
        if (optRuleEntity.isEmpty()) {
            String msgSource = messageSource.getMessage("exception.rule.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
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
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Rule updateRule(final Integer id
                            , final Rule rule)
                            throws MyException {
        // Rule does not exist
        Optional<Rule> optRuleEntity = ruleRepository.findById(id);
        if (optRuleEntity.isEmpty()) {
            String msgSource = messageSource.getMessage("exception.rule.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Rule parameter is null
        if (rule == null) {
            String msgSource = messageSource.getMessage("exception.rule.nullRule"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
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
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteRule(final Integer id)
                            throws MyException {
        // Rule does not exist
        if (ruleRepository.existsById(id) == false) {
            String msgSource = messageSource.getMessage("exception.rule.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Rule deleted
        ruleRepository.deleteById(id);
    }
}

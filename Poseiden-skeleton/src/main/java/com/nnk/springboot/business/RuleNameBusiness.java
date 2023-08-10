package com.nnk.springboot.business;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exception.MyExceptionBadRequestException;
import com.nnk.springboot.exception.MyExceptionNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * RuleNameBusiness is the RuleNames page processing service
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Service
public class RuleNameBusiness {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Find list of RuleNames
     *
     * @return List of RuleNames
     */
    public List<RuleName> getRuleNamesList() {
        return ruleNameRepository.findAll();
    }

    /**
     * Create new RuleName
     *
     * @param ruleName The RuleName object added
     *
     * @return RuleName added
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public RuleName createRuleName(final RuleName ruleName)
            throws MyExceptionBadRequestException {
        // RuleName parameter is null
        if (ruleName == null) {
            log.debug("THROW, RuleName is null.");
            throw new MyExceptionBadRequestException("throw.ruleName.nullRuleName");
        }
        // RuleName exist
        Integer id = ruleName.getId();
        if (id != null) {
            Optional<RuleName> optRuleNameEntity = ruleNameRepository.findById(id);
            if (optRuleNameEntity.isPresent()) {
                log.debug("THROW, RuleName exist ({}).", optRuleNameEntity.get());
                throw new MyExceptionBadRequestException("throw.ruleName.ruleNameExists");
            }
        }
        // RuleName saved
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Find RuleName
     *
     * @param id RuleName ID founded
     *
     * @return RuleName founded
     * @throws MyExceptionNotFoundException Exception not found
     */
    public RuleName getRuleNameById(final Integer id)
            throws MyExceptionNotFoundException {
        // RuleName does not exist
        Optional<RuleName> optRuleNameEntity = ruleNameRepository.findById(id);
        if (optRuleNameEntity.isPresent() == false) {
            log.debug("THROW, RuleName not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.ruleName.unknown", id);
        }
        // RuleName found
        return optRuleNameEntity.get();
    }

    /**
     * Updated RuleName
     *
     * @param id RuleName ID updated
     * @param ruleName The RuleName object updated
     *
     * @return RuleName updated
     * @throws MyExceptionNotFoundException Exception not found
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public RuleName updateRuleName(final Integer id
            , final RuleName ruleName)
            throws MyExceptionNotFoundException {
        // RuleName does not exist
        Optional<RuleName> optRuleNameEntity = ruleNameRepository.findById(id);
        if (optRuleNameEntity.isPresent() == false) {
            log.debug("THROW, RuleName not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.ruleName.unknown", id);
        }
        // RuleName parameter is null
        if (ruleName == null) {
            log.debug("THROW, RuleName is null.");
            throw new MyExceptionBadRequestException("throw.ruleName.nullRuleName");
        }
        // RuleName updated
        RuleName ruleNameEntity = optRuleNameEntity.get();
        ruleNameEntity.setName(ruleName.getName());
        ruleNameEntity.setDescription(ruleName.getDescription());
        ruleNameEntity.setJson(ruleName.getJson());
        ruleNameEntity.setTemplate(ruleName.getTemplate());
        ruleNameEntity.setSqlStr(ruleName.getSqlStr());
        ruleNameEntity.setSqlPart(ruleName.getSqlPart());
        return ruleNameRepository.save(ruleNameEntity);
    }

    /**
     * Deleted RuleName
     *
     * @param id RuleName ID deleted
     *
     * @throws MyExceptionNotFoundException Exception not found
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteRuleName(final Integer id)
            throws MyExceptionNotFoundException {
        // RuleName does not exist
        Optional<RuleName> optRuleNameEntity = ruleNameRepository.findById(id);
        if (optRuleNameEntity.isPresent() == false) {
            log.debug("THROW, RuleName not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.ruleName.unknown", id);
        }
        // RuleName deleted
        ruleNameRepository.deleteById(id);
    }
}

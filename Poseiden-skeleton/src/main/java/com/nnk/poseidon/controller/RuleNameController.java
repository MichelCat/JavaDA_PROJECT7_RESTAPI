package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.RuleNameBusiness;
import com.nnk.poseidon.model.Rule;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * RuleNameController is the Endpoint will perform the following actions via Get/Post/Put/Delete with HTTP on Rule.
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("ruleName")
public class RuleNameController {
    @Autowired
    private RuleNameBusiness ruleNameBusiness;
    @Autowired
    private MessageSource messageSource;

    /**
     * Read - Get the list of Rules.
     *
     * @param model Model object
     * @return View
     */
    @GetMapping("/list")
    public String home(Model model)
    {
        // Find all Rule, add to model
        String msgSource = messageSource.getMessage("debug.rule.listForm"
                                , null, LocaleContextHolder.getLocale());
        log.debug("HTTP GET, " + msgSource);
        model.addAttribute("rules", ruleNameBusiness.getRulesList());
        return "ruleName/list";
    }

    /**
     * Read - Page add new Rule.
     *
     * @param rule The Rule object
     * @return View
     */
    @GetMapping("/add")
    public String addRuleForm(Rule rule) {
        String msgSource = messageSource.getMessage("debug.rule.addForm"
                                , null, LocaleContextHolder.getLocale());
        log.debug("HTTP GET, " + msgSource);
        return "ruleName/add";
    }

    /**
     * Create - Add new Rule
     *
     * @param rule The Rule object added
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PostMapping("/validate")
    public String validate(@Valid Rule rule
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check data valid and save to db, after saving return Rule list

        // Rule parameter is not valid
        if (result.hasErrors()) {
            String msgSource = messageSource.getMessage("debug.rule.validation"
                                    , new Object[] { rule }
                                    , LocaleContextHolder.getLocale());
            log.debug("HTTP POST, " + msgSource);
            return "ruleName/add";
        }
        try {
            // Save Rule
            ruleNameBusiness.createRule(rule);
            String msgSource = messageSource.getMessage("info.rule.created"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP POST, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
            model.addAttribute("rules", ruleNameBusiness.getRulesList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/ruleName/list";
    }

    /**
     * Read - Page add new Rule.
     *
     * @param id Rule ID added
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     * @return View
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Get Rule by Id and to model then show to the form
        try {
            Rule rule = ruleNameBusiness.getRuleById(id);
            String msgSource = messageSource.getMessage("debug.rule.updateForm"
                                    , null, LocaleContextHolder.getLocale());
            log.debug("HTTP GET, " + msgSource);
            model.addAttribute("rule", rule);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ruleName/list";
        }
        return "ruleName/update";
    }

    /**
     * Update - Update an existing Rule
     *
     * @param id Rule ID added
     * @param rule The Rule object updated
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PatchMapping("/update/{id}")
    public String updateRule(@PathVariable("id") Integer id
                                , @Valid Rule rule
                                , BindingResult result
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Check required fields, if valid call service to update Rule and return Rule list

        // Rule parameter is not valid
        if (result.hasErrors()) {
            String msgSource = messageSource.getMessage("debug.rule.validation"
                                    , new Object[] { rule }
                                    , LocaleContextHolder.getLocale());
            log.debug("HTTP PATCH, " + msgSource);
            return "ruleName/update";
        }
        try {
            // Modify Rule
            ruleNameBusiness.updateRule(id, rule);
            String msgSource = messageSource.getMessage("info.rule.updated"
                                        , null, LocaleContextHolder.getLocale());
            log.info("HTTP PATCH, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
            model.addAttribute("rules", ruleNameBusiness.getRulesList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/ruleName/list";
    }

    /**
     * Delete - Delete an Rule
     *
     * @param id Rule ID deleted
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @GetMapping("/delete/{id}")
    public String deleteRule(@PathVariable("id") Integer id
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Find Rule by Id and delete the Rule, return to Rule list

        try {
            // Delete Rule
            ruleNameBusiness.deleteRule(id);
            String msgSource = messageSource.getMessage("info.rule.deleted"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP DELETE, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
            model.addAttribute("rules", ruleNameBusiness.getRulesList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/ruleName/list";
    }
}

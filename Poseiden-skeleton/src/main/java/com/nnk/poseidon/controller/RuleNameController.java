package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.RuleNameBusiness;
import com.nnk.poseidon.model.Rule;
import com.nnk.poseidon.exception.MessagePropertieFormat;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        log.debug("HTTP GET, display rule name list form.");
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
        log.debug("HTTP GET, display Rule add form.");
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
            log.debug("HTTP GET, Validation failed for Rule ({}).", rule);
            return "ruleName/add";
        }
        try {
            // Save Rule
            Rule ruleSave = ruleNameBusiness.createRule(rule);
            log.info("HTTP GET, SUCCESSFUL ({}).", ruleSave);
            redirectAttributes.addFlashAttribute("success", "Rule was created successfully.");
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
            log.debug("HTTP GET, display Rule update form ({}).", rule);
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

        // Rule ID parameter is null or zero
        if (id == null || id == 0) {
            log.debug(MessagePropertieFormat.getMessage("rule.error.template", id));
            model.addAttribute("errorMessage", MessagePropertieFormat.getMessage("rule.error.template", id));
            return "ruleName/update";
        }
        // Rule parameter is not valid
        if (result.hasErrors()) {
            log.debug("HTTP PATCH, Validation failed for Rule ({}).", rule);
            return "ruleName/update";
        }
        try {
            // Modify Rule
            Rule ruleSave = ruleNameBusiness.updateRule(id, rule);
            log.info("HTTP PATCH, SUCCESSFUL ({}).", ruleSave);
            redirectAttributes.addFlashAttribute("success", "Rule was created successfully.");
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

        // Rule ID parameter is null or zero
        if (id == null || id == 0) {
            log.debug(MessagePropertieFormat.getMessage("rule.error.template", id));
            redirectAttributes.addFlashAttribute("errorMessage", MessagePropertieFormat.getMessage("rule.error.template", id));
            return "redirect:/ruleName/list";
        }
        try {
            // Delete Rule
            ruleNameBusiness.deleteRule(id);
            log.info("HTTP DELETE, SUCCESSFUL (Rule ID : {}).", id);
            redirectAttributes.addFlashAttribute("success", "Rule successfully deleted.");
            model.addAttribute("rules", ruleNameBusiness.getRulesList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/ruleName/list";
    }
}

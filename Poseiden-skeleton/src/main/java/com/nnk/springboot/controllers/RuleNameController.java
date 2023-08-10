package com.nnk.springboot.controllers;

import com.nnk.springboot.business.RuleNameBusiness;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exception.MessagePropertieFormat;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * RuleNameController is the Endpoint will perform the following actions via Get/Post/Put/Delete with HTTP on RuleName.
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
     * Read - Get the list of RuleNames.
     *
     * @param model Model object
     * @return View
     */
    @GetMapping("/list")
    public String home(Model model)
    {
        // Find all RuleName, add to model
        log.debug("HTTP GET, display home form.");
        model.addAttribute("ruleNames", ruleNameBusiness.getRuleNamesList());
        return "ruleName/list";
    }

    /**
     * Read - Page add new RuleName.
     *
     * @param ruleName The RuleName object
     * @return View
     */
    @GetMapping("/add")
    public String addRuleForm(RuleName ruleName) {
        log.debug("HTTP GET, display RuleName add form.");
        return "ruleName/add";
    }

    /**
     * Create - Add new RuleName
     *
     * @param ruleName The RuleName object added
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PostMapping("/validate")
    public String validate(@Valid RuleName ruleName
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check data valid and save to db, after saving return RuleName list

        // RuleName parameter is not valid
        if (result.hasErrors()) {
            log.debug("HTTP GET, Validation failed for RuleName ({}).", ruleName);
            return "ruleName/add";
        }
        try {
            // Save RuleName
            RuleName ruleNameSave = ruleNameBusiness.createRuleName(ruleName);
            log.info("HTTP GET, SUCCESSFUL ({}).", ruleNameSave);
            redirectAttributes.addFlashAttribute("success", "RuleName was created successfully.");
            model.addAttribute("ruleNames", ruleNameBusiness.getRuleNamesList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/ruleName/list";
    }

    /**
     * Read - Page add new RuleName.
     *
     * @param id RuleName ID added
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     * @return View
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Get RuleName by Id and to model then show to the form
        try {
            RuleName ruleName = ruleNameBusiness.getRuleNameById(id);
            log.debug("HTTP GET, display RuleName update form ({}).", ruleName);
            model.addAttribute("ruleName", ruleName);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ruleName/list";
        }
        return "ruleName/update";
    }

    /**
     * Update - Update an existing RuleName
     *
     * @param id RuleName ID added
     * @param ruleName The RuleName object updated
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PatchMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id
                                , @Valid RuleName ruleName
                                , BindingResult result
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Check required fields, if valid call service to update RuleName and return RuleName list

        // RuleName ID parameter is null or zero
        if (id == null || id == 0) {
            log.debug(MessagePropertieFormat.getMessage("ruleName.error.template", id));
            model.addAttribute("errorMessage", MessagePropertieFormat.getMessage("ruleName.error.template", id));
            return "ruleName/update";
        }
        // RuleName parameter is not valid
        if (result.hasErrors()) {
            log.debug("HTTP PATCH, Validation failed for RuleName ({}).", ruleName);
            return "ruleName/update";
        }
        try {
            // Modify RuleName
            RuleName ruleNameSave = ruleNameBusiness.updateRuleName(id, ruleName);
            log.info("HTTP PATCH, SUCCESSFUL ({}).", ruleNameSave);
            redirectAttributes.addFlashAttribute("success", "RuleName was created successfully.");
            model.addAttribute("ruleNames", ruleNameBusiness.getRuleNamesList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/ruleName/list";
    }

    /**
     * Delete - Delete an RuleName
     *
     * @param id RuleName ID deleted
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Find RuleName by Id and delete the RuleName, return to Rule list

        // RuleName ID parameter is null or zero
        if (id == null || id == 0) {
            log.debug(MessagePropertieFormat.getMessage("ruleName.error.template", id));
            redirectAttributes.addFlashAttribute("errorMessage", MessagePropertieFormat.getMessage("ruleName.error.template", id));
            return "redirect:/ruleName/list";
        }
        try {
            // Delete RuleName
            ruleNameBusiness.deleteRuleName(id);
            log.info("HTTP DELETE, SUCCESSFUL (RuleName ID : {}).", id);
            redirectAttributes.addFlashAttribute("success", "RuleName successfully deleted.");
            model.addAttribute("ruleNames", ruleNameBusiness.getRuleNamesList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/ruleName/list";
    }
}

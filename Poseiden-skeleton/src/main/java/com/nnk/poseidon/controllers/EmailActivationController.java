package com.nnk.poseidon.controllers;

import com.nnk.poseidon.business.EmailActivationBusiness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * EmailActivationController is the Endpoint of for email activation pages
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("app")
public class EmailActivationController {

    @Autowired
    private EmailActivationBusiness emailActivationBusiness;

    /**
     * Update - account activation
     *
     * @param validationKey Validation key
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @GetMapping("/register/{key}")
    public String accountActivation(@PathVariable("key") final String validationKey
            , Model model
            , RedirectAttributes redirectAttributes) {
        try {
            // Account activation
            emailActivationBusiness.activatedUser(validationKey);
            log.info("HTTP GET, SUCCESSFUL ACCOUNT ACTIVATION");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/app/login";
    }
}

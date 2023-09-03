package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.LanguageBusiness;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * LanguageController is the endpoint of the language change page
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Controller
public class LanguageController {

    @Autowired
    private LanguageBusiness languageBusiness;
    @Autowired
    private MessageSource messageSource;

    /**
     * Read - Change language.
     *
     * @param optLang New language
     * @param optSourcePage Source page
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @GetMapping("/change-language")
    public String changeLanguage(@RequestParam("lang") Optional<String> optLang
                                , @RequestParam("source-page") Optional<String> optSourcePage
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        try {
            if (optSourcePage.isEmpty()) {
                return "redirect:login";
            }
            languageBusiness.changeLanguage(optLang);
            String msgSource = messageSource.getMessage("info.changeLanguage.success"
                                , null, LocaleContextHolder.getLocale());
            log.info("HTTP GET, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:" + optSourcePage.get();
    }
}

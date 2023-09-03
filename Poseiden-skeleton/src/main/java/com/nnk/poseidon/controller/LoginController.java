package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.LoginBusiness;
import com.nnk.poseidon.model.Register;
import com.nnk.poseidon.model.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

/**
 * LoginController is the Endpoint of for login page
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("app")
public class LoginController {

    @Autowired
    private LoginBusiness loginBusiness;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }

    /**
     * Read - Get Login Page
     *
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     * @param params Parameter list
     * @return View
     */
    @GetMapping("/login")
    public String getLogin(Model model
            , RedirectAttributes redirectAttributes
            , @RequestParam Map<String,String> params) {
        if (params.containsKey("logout")) {
            model.addAttribute("logout", true);
        }
        if (params.containsKey("error")) {
            model.addAttribute("error", true);
        }
        // User record exists
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    /**
     * Read - Get Register Page
     *
     * @param register The User object
     * @return View
     */
    @GetMapping("/register")
    public String getRegister(Register register) {
        return "register";
    }

    /**
     * Create - Register with user
     *
     * @param register Register record
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PostMapping("/register")
    public String postRegister(@ModelAttribute @Valid Register register
                                , BindingResult result
                                , Model model
                                , RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
        String msgSource = messageSource.getMessage("debug.register.validation"
                                , new Object[] { register }
                                , LocaleContextHolder.getLocale());
        log.debug("HTTP POST, " + msgSource);
        return "register";
    }

    try {
        // Adding the new user
        loginBusiness.addUser(register);
        String msgSource = messageSource.getMessage("info.register.created"
                                , null, LocaleContextHolder.getLocale());
        log.info("HTTP POST, " + msgSource);
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/app/register";
    }
    return "redirect:/app/login";
    }
}

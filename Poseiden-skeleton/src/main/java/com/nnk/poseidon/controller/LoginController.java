package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.LoginBusiness;
import com.nnk.poseidon.model.Register;
import com.nnk.poseidon.model.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping("login")
//    public ModelAndView login() {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("login");
//        return mav;
//    }
//
//    @GetMapping("secure/article-details")
//    public ModelAndView getAllUserArticles() {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("users", userRepository.findAll());
//        mav.setViewName("user/list");
//        return mav;
//    }
//
//    @GetMapping("error")
//    public ModelAndView error() {
//        ModelAndView mav = new ModelAndView();
//        String errorMessage= "You are not authorized for the requested data.";
//        mav.addObject("errorMsg", errorMessage);
//        mav.setViewName("403");
//        return mav;
//    }

    @Autowired
    private LoginBusiness loginBusiness;

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
        log.debug("HTTP POST, Validation failed for register ({}).", register);
        return "register";
    }

    try {
        // Adding the new user
        loginBusiness.addUser(register);
        log.info("HTTP POST, SUCCESSFUL ({}).", register);
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/app/register";
    }
    return "redirect:/app/login";
    }
}

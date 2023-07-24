package com.nnk.springboot.controllers;

import com.nnk.springboot.business.LoginBusiness;
import com.nnk.springboot.domain.Register;
import com.nnk.springboot.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
//@RequestMapping("app")
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
     * Read - Get Login Page Attributes
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
     * Read - Get Register Page Attributes
     *
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     * @return View
     */
    @GetMapping("/register")
    public String getRegister(Model model
            , RedirectAttributes redirectAttributes) {
        // New user record
        Register register = new Register();
        model.addAttribute("user", register);
        return "register";
    }

    /**
     * Create - Register with user
     *
     * @param register Register record
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PostMapping("/register")
    public String postRegister(@ModelAttribute Register register
            , Model model
            , RedirectAttributes redirectAttributes) {
        try {
            // Adding the new user
            loginBusiness.addCustomer(register);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
        return "redirect:/login";
    }

}

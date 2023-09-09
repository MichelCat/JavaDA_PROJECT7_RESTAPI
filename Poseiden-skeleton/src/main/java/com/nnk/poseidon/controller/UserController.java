package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.UserBusiness;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * UserController is the Endpoint will perform the following actions via Get/Post/Put/Delete with HTTP on User.
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserBusiness userBusiness;
    @Autowired
    private MessageSource messageSource;

    /**
     * Read - Get the list of registers.
     *
     * @param model Model object
     * @return View
     */
    @GetMapping("/list")
    public String home(Model model)
    {
        // Find all User, add to model
        String msgSource = messageSource.getMessage("debug.user.listForm"
                                , null, LocaleContextHolder.getLocale());
        log.debug("HTTP GET, " + msgSource);
        model.addAttribute("registers", userBusiness.getUsersList());
        return "user/list";
    }

    /**
     * Read - Page add new User.
     *
     * @param register The User object
     * @return View
     */
    @GetMapping("/add")
    public String addUser(Register register) {
        String msgSource = messageSource.getMessage("debug.user.addForm"
                                , null, LocaleContextHolder.getLocale());
        log.debug("HTTP GET, " + msgSource);
        return "user/add";
    }

    /**
     * Create - Add new User
     *
     * @param register The User object added
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PostMapping("/validate")
    public String validate(@Valid Register register
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check data valid and save to db, after saving return user list

        // Check null role
        if (register.getRole() == null) {
            result.addError(new FieldError("register", "role"
                            , messageSource.getMessage("constraint.notNull.user.role"
                            , null, LocaleContextHolder.getLocale())));
        }
        // User parameter is not valid
        if (result.hasErrors()) {
            String msgSource = messageSource.getMessage("debug.user.validation"
                                    , new Object[] { register }
                                    , LocaleContextHolder.getLocale());
            log.debug("HTTP POST, " + msgSource);
            return "user/add";
        }
        try {
            // Save User
            userBusiness.createUser(register);
            String msgSource = messageSource.getMessage("info.user.created"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP POST, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/user/list";
    }

    /**
     * Read - Page add new User.
     *
     * @param id User ID added
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     * @return View
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Get User by Id and to model then show to the form
        try {
            Register register = userBusiness.getRegisterById(id);
            register.setPassword("");
            String msgSource = messageSource.getMessage("debug.user.updateForm"
                                    , null, LocaleContextHolder.getLocale());
            log.debug("HTTP GET, " + msgSource);
            model.addAttribute("register", register);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/list";
        }
        return "user/update";
    }

    /**
     * Update - Update an existing User
     *
     * @param id User ID added
     * @param register The User object updated
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PatchMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id
                            , @Valid Register register
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check required fields, if valid call service to update Curve and return Curve list

        // User parameter is not valid
        if (result.hasErrors()) {
            String msgSource = messageSource.getMessage("debug.user.validation"
                                    , new Object[] { register }
                                    , LocaleContextHolder.getLocale());
            log.debug("HTTP PATCH, " + msgSource);
            return "user/update";
        }
        try {
            // Modify User
            userBusiness.updateUser(id, register);
            String msgSource = messageSource.getMessage("info.user.updated"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP PATCH, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/user/list";
    }

    /**
     * Delete - Delete an User
     *
     * @param id User ID deleted
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Find Curve by Id and delete the Curve, return to Curve list

        try {
            // Delete User
            userBusiness.deleteUser(id);
            String msgSource = messageSource.getMessage("info.user.deleted"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP DELETE, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/user/list";
    }
}

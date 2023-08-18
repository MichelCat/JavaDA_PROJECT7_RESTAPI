package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.UserBusiness;
import com.nnk.poseidon.model.Register;
import com.nnk.poseidon.model.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        log.debug("HTTP GET, display user list form.");
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
        log.debug("HTTP GET, display User add form.");
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
                    , "Role must not be null"));
            return "user/add";
        }
        // User parameter is not valid
        if (result.hasErrors()) {
            log.debug("HTTP GET, Validation failed for User ({}).", register);
            return "user/add";
        }
        try {
            // Save User
            User userSave = userBusiness.createUser(register);
            log.info("HTTP GET, SUCCESSFUL ({}).", userSave);
            redirectAttributes.addFlashAttribute("successMessage", "User created successfully.");
            model.addAttribute("registers", userBusiness.getUsersList());
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
            log.debug("HTTP GET, display User update form ({}).", register);
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
            log.debug("HTTP PATCH, Validation failed for User ({}).", register);
            return "user/update";
        }
        try {
            // Modify User
            User userSave = userBusiness.updateUser(id, register);
            log.info("HTTP PATCH, SUCCESSFUL ({}).", userSave);
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully.");
            model.addAttribute("registers", userBusiness.getUsersList());
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
            log.info("HTTP DELETE, SUCCESSFUL (User ID : {}).", id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
            model.addAttribute("registers", userBusiness.getUsersList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/user/list";
    }
}

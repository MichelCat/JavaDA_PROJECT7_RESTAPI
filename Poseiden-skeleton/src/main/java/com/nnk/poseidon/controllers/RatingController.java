package com.nnk.poseidon.controllers;

import com.nnk.poseidon.business.RatingBusiness;
import com.nnk.poseidon.domain.Rating;
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
 * RatingController is the Endpoint will perform the following actions via Get/Post/Put/Delete with HTTP on rating.
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("rating")
public class RatingController {
    @Autowired
    private RatingBusiness ratingBusiness;

    /**
     * Read - Get the list of Ratings.
     *
     * @param model Model object
     * @return View
     */
    @GetMapping("/list")
    public String home(Model model)
    {
        // Find all Rating, add to model
        log.debug("HTTP GET, display home form.");
        model.addAttribute("ratings", ratingBusiness.getRatingsList());
        return "rating/list";
    }

    /**
     * Read - Page add new Rating.
     *
     * @param rating The Rating object
     * @return View
     */
    @GetMapping("/add")
    public String addRatingForm(Rating rating) {
        log.debug("HTTP GET, display Rating add form.");
        return "rating/add";
    }

    /**
     * Create - Add new Rating
     *
     * @param rating The Rating object added
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PostMapping("/validate")
    public String validate(@Valid Rating rating
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check data valid and save to db, after saving return Rating list

        // Rating parameter is not valid
        if (result.hasErrors()) {
            log.debug("HTTP GET, Validation failed for Rating ({}).", rating);
            return "rating/add";
        }
        try {
            // Save Rating
            Rating ratingSave = ratingBusiness.createRating(rating);
            log.info("HTTP GET, SUCCESSFUL ({}).", ratingSave);
            redirectAttributes.addFlashAttribute("success", "Rating was created successfully.");
            model.addAttribute("ratings", ratingBusiness.getRatingsList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/rating/list";
    }

    /**
     * Read - Page add new Rating.
     *
     * @param id Rating ID added
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     * @return View
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Get Rating by Id and to model then show to the form
        try {
            Rating rating = ratingBusiness.getRatingById(id);
            log.debug("HTTP GET, display Rating update form ({}).", rating);
            model.addAttribute("rating", rating);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/rating/list";
        }
        return "rating/update";
    }

    /**
     * Update - Update an existing Rating
     *
     * @param id Rating ID added
     * @param rating The Rating object updated
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PatchMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id
                                , @Valid Rating rating
                                , BindingResult result
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Check required fields, if valid call service to update Rating and return Rating list

        // Rating ID parameter is null or zero
        if (id == null || id == 0) {
            log.debug(MessagePropertieFormat.getMessage("rating.error.template", id));
            model.addAttribute("errorMessage", MessagePropertieFormat.getMessage("rating.error.template", id));
            return "rating/update";
        }
        // Rating parameter is not valid
        if (result.hasErrors()) {
            log.debug("HTTP PATCH, Validation failed for Rating ({}).", rating);
            return "rating/update";
        }
        try {
            // Modify Rating
            Rating ratingSave = ratingBusiness.updateRating(id, rating);
            log.info("HTTP PATCH, SUCCESSFUL ({}).", ratingSave);
            redirectAttributes.addFlashAttribute("success", "Rating was created successfully.");
            model.addAttribute("ratings", ratingBusiness.getRatingsList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/rating/list";
    }

    /**
     * Delete - Delete an Rating
     *
     * @param id Rating ID deleted
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Find Rating by Id and delete the Rating, return to Rating list

        // Rating ID parameter is null or zero
        if (id == null || id == 0) {
            log.debug(MessagePropertieFormat.getMessage("rating.error.template", id));
            redirectAttributes.addFlashAttribute("errorMessage", MessagePropertieFormat.getMessage("rating.error.template", id));
            return "redirect:/rating/list";
        }
        try {
            // Delete Rating
            ratingBusiness.deleteRating(id);
            log.info("HTTP DELETE, SUCCESSFUL (Rating ID : {}).", id);
            redirectAttributes.addFlashAttribute("success", "Rating successfully deleted.");
            model.addAttribute("ratings", ratingBusiness.getRatingsList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/rating/list";
    }
}

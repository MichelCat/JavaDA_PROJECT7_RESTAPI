package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.RatingBusiness;
import com.nnk.poseidon.model.Rating;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    @Autowired
    private MessageSource messageSource;

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
        String msgSource = messageSource.getMessage("debug.rating.listForm"
                                , null, LocaleContextHolder.getLocale());
        log.debug("HTTP GET, " + msgSource);
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
        String msgSource = messageSource.getMessage("debug.rating.addForm"
                                , null, LocaleContextHolder.getLocale());
        log.debug("HTTP GET, " + msgSource);
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
            String msgSource = messageSource.getMessage("debug.rating.validation"
                                    , new Object[] { rating }
                                    , LocaleContextHolder.getLocale());
            log.debug("HTTP POST, " + msgSource);
            return "rating/add";
        }
        try {
            // Save Rating
            ratingBusiness.createRating(rating);
            String msgSource = messageSource.getMessage("info.rating.created"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP POST, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
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
            String msgSource = messageSource.getMessage("debug.rating.updateForm"
                                    , null, LocaleContextHolder.getLocale());
            log.debug("HTTP GET, " + msgSource);
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

        // Rating parameter is not valid
        if (result.hasErrors()) {
            String msgSource = messageSource.getMessage("debug.rating.validation"
                                    , new Object[] { rating }
                                    , LocaleContextHolder.getLocale());
            log.debug("HTTP PATCH, " + msgSource);
            return "rating/update";
        }
        try {
            // Modify Rating
            ratingBusiness.updateRating(id, rating);
            String msgSource = messageSource.getMessage("info.rating.updated"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP PATCH, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
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

        try {
            // Delete Rating
            ratingBusiness.deleteRating(id);
            String msgSource = messageSource.getMessage("info.rating.deleted"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP DELETE, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/rating/list";
    }
}

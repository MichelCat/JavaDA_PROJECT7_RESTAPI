package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.BidListBusiness;
import com.nnk.poseidon.model.Bid;
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
 * BidListController is the Endpoint will perform the following actions via Get/Post/Put/Delete with HTTP on bids.
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("bidList")
public class BidListController {
    @Autowired
    private BidListBusiness bidListBusiness;
    @Autowired
    private MessageSource messageSource;

    /**
     * Read - Get the list of bids.
     *
     * @param model Model object
     * @return View
     */
    @GetMapping("/list")
    public String home(Model model)
    {
        // Call service find all bids to show to the view
        String msgSource = messageSource.getMessage("debug.bid.listForm"
                                , null, LocaleContextHolder.getLocale());
        log.debug("HTTP GET, " + msgSource);
        model.addAttribute("bids", bidListBusiness.getBidsList());
        return "bidList/list";
    }

    /**
     * Read - Page add new bid.
     *
     * @param bid The bid object
     * @return View
     */
    @GetMapping("/add")
    public String addBidForm(Bid bid) {
        String msgSource = messageSource.getMessage("debug.bid.addForm"
                                , null, LocaleContextHolder.getLocale());
        log.debug("HTTP GET, " + msgSource);
        return "bidList/add";
    }

    /**
     * Create - Add new bid
     *
     * @param bid The bid object added
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PostMapping("/validate")
    public String validate(@Valid Bid bid
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check data valid and save to db, after saving return bid list

        // Bid parameter is not valid
        if (result.hasErrors()) {
            String msgSource = messageSource.getMessage("debug.bid.validation"
                                    , new Object[] { bid }
                                    , LocaleContextHolder.getLocale());
            log.debug("HTTP POST, " + msgSource);
            return "bidList/add";
        }
        try {
            // Save bid
            bidListBusiness.createBid(bid);
            String msgSource = messageSource.getMessage("info.bid.created"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP POST, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/bidList/list";
    }

    /**
     * Read - Page add new bid.
     *
     * @param id Bid ID added
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     * @return View
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Get Bid by Id and to model then show to the form
        try {
            Bid bid = bidListBusiness.getBidById(id);
            String msgSource = messageSource.getMessage("debug.bid.updateForm"
                                    , null, LocaleContextHolder.getLocale());
            log.debug("HTTP GET, " + msgSource);
            model.addAttribute("bid", bid);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/bidList/list";
        }
        return "bidList/update";
    }

    /**
     * Update - Update an existing bid
     *
     * @param id Bid ID added
     * @param bid The bid object updated
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PatchMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id
                            , @Valid Bid bid
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check required fields, if valid call service to update Bid and return list Bid

        // Bid parameter is not valid
        if (result.hasErrors()) {
            String msgSource = messageSource.getMessage("debug.bid.validation"
                                    , new Object[] { bid }
                                    , LocaleContextHolder.getLocale());
            log.debug("HTTP PATCH, " + msgSource);
            return "bidList/update";
        }
        try {
            // Modify bid
            bidListBusiness.updateBid(id, bid);
            String msgSource = messageSource.getMessage("info.bid.updated"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP PATCH, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/bidList/list";
    }

    /**
     * Delete - Delete an bid
     *
     * @param id Bid ID deleted
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Find Bid by Id and delete the bid, return to Bid list

        try {
            // Delete bid
            bidListBusiness.deleteBid(id);
            String msgSource = messageSource.getMessage("info.bid.deleted"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP DELETE, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/bidList/list";
    }
}

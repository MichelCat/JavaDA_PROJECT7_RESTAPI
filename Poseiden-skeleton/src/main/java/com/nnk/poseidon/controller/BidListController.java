package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.BidListBusiness;
import com.nnk.poseidon.model.Bid;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        log.debug("HTTP GET, display bid list form.");
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
        log.debug("HTTP GET, display bid add form.");
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
            log.debug("HTTP GET, Validation failed for bid ({}).", bid);
            return "bidList/add";
        }
        try {
            // Save bid
            Bid bidSave = bidListBusiness.createBid(bid);
            log.info("HTTP GET, SUCCESSFUL ({}).", bidSave);
            redirectAttributes.addFlashAttribute("successMessage", "Bid created successfully.");
            model.addAttribute("bids", bidListBusiness.getBidsList());
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
            log.debug("HTTP GET, display bid update form ({}).", bid);
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
            log.debug("HTTP PATCH, Validation failed for bid ({}).", bid);
            return "bidList/update";
        }
        try {
            // Modify bid
            Bid bidSave = bidListBusiness.updateBid(id, bid);
            log.info("HTTP PATCH, SUCCESSFUL ({}).", bidSave);
            redirectAttributes.addFlashAttribute("successMessage", "Bid updated successfully.");
            model.addAttribute("bids", bidListBusiness.getBidsList());
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
            log.info("HTTP DELETE, SUCCESSFUL (Bid ID : {}).", id);
            redirectAttributes.addFlashAttribute("successMessage", "Bid deleted successfully.");
            model.addAttribute("bids", bidListBusiness.getBidsList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/bidList/list";
    }
}

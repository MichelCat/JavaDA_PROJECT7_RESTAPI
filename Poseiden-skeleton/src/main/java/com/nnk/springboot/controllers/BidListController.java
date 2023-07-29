package com.nnk.springboot.controllers;

import com.nnk.springboot.business.BidListBusiness;
import com.nnk.springboot.domain.BidList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * BidListController is the Endpoint will perform the following actions via Get/Post/Put/Delete with HTTP on bids.
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Validated
@Controller
public class BidListController {
    @Autowired
    private BidListBusiness bidListBusiness;

    /**
     * Read - Get the list of bids.
     *
     * @param model Model object
     * @return View
     */
//    @RequestMapping("/bidList/list")
    @GetMapping("/bidList/list")
    public String home(Model model)
    {
        // Call service find all bids to show to the view
        log.debug("HTTP GET, display home form.");
        model.addAttribute("bidList", bidListBusiness.getBidsList());
        return "bidList/list";
    }

    /**
     * Read - Page add new bid.
     *
     * @param bid The bid object
     * @return View
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
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
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check data valid and save to db, after saving return bid list
        if (result.hasErrors()) {
            log.info("HTTP GET, Validation failed for bid ({}).", bid);
            return "bidList/add";
        }
        BidList bidSave = bidListBusiness.createBid(bid);
        log.info("HTTP GET, SUCCESSFUL ({}).", bidSave);
        redirectAttributes.addFlashAttribute("success", "Bid was created successfully.");
        model.addAttribute("bidList", bidListBusiness.getBidsList());
        return "redirect:/bidList/list";
    }

    /**
     * Read - Page add new bid.
     *
     * @param id Bid ID added
     * @param model Model object
     * @return View
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") @Positive Integer id
                                , Model model) {
        // Get Bid by Id and to model then show to the form

//        Optional<BidList> optBidList = bidListBusiness.getBidById(id);
//        if (optBidList.isPresent() == false) {
//            redirectAttributes.addFlashAttribute("error", "Bid (" + id + ") does not exist.");
//            return "redirect:/bidList/list";
//        }
//        model.addAttribute("bidList", optBidList.get());
//        return "bidList/update";

        BidList bid = bidListBusiness.getBidById(id);
        log.debug("HTTP GET, display bid update form ({}).", bid);
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }

    /**
     * Update - Update an existing bid
     *
     * @param id Bid ID added
     * @param bidList The bid object updated
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
//    @PatchMapping("/bidList/update/{id}")
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") @Positive Integer id
                            , @Valid BidList bidList
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
//            bidList.setBidListId(id);
//            model.addAttribute("bid", bidList);
            log.info("HTTP POST, Validation failed for bid ({}).", bidList);
            return "bidList/update";
        }
        BidList bidSave = bidListBusiness.updateBid(id, bidList);
        log.info("HTTP POST, SUCCESSFUL ({}).", bidSave);
        redirectAttributes.addFlashAttribute("success", "Bid was created successfully.");
        model.addAttribute("bidList", bidListBusiness.getBidsList());
        return "redirect:/bidList/list";
    }

    /**
     * Delete - Delete an fire station
     *
     * @param id Bid ID deleted
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") @Positive Integer id
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Find Bid by Id and delete the bid, return to Bid list

//        if (bidListBusiness.deleteBid(id) == false) {
//            model.addAttribute("error", "Bid (" + id + ") does not exist.");
//        } else {
//            model.addAttribute("success", "Bid successfully deleted.");
//        }
//        model.addAttribute("bidList", bidListBusiness.getBidsList());
//        return "redirect:/bidList/list";

        bidListBusiness.deleteBid(id);
        log.info("HTTP DELETE, SUCCESSFUL (Bid ID : {}).", id);
        redirectAttributes.addFlashAttribute("success", "Bid successfully deleted.");
        model.addAttribute("bidList", bidListBusiness.getBidsList());
        return "redirect:/bidList/list";
    }
}

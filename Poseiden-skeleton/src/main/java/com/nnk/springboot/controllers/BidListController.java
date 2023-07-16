package com.nnk.springboot.controllers;

import com.nnk.springboot.business.BidListBusiness;
import com.nnk.springboot.domain.BidList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Slf4j
@Controller
public class BidListController {
    @Autowired
    private BidListBusiness bidListBusiness;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        // Call service find all bids to show to the view
        model.addAttribute("bidList", bidListBusiness.getBidsList());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check data valid and save to db, after saving return bid list
        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListBusiness.createBid(bid);
        model.addAttribute("success", "Bid was created successfully.");
        model.addAttribute("bidList", bidListBusiness.getBidsList());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Get Bid by Id and to model then show to the form

//        Optional<BidList> optBidList = bidListBusiness.getBidById(id);
//        if (optBidList.isPresent() == false) {
//            model.addAttribute("error", "Bid (" + id + ") does not exist.");
//            return "redirect:/bidList/list";
//        }
//        model.addAttribute("bidList", optBidList.get());
//        return "bidList/update";

        BidList bid = bidListBusiness.getBidById(id);
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id
                            , @Valid BidList bidList
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
//            bidList.setBidListId(id);
//            model.addAttribute("bid", bidList);
            return "bidList/update";
        }
        bidListBusiness.updateBid(id, bidList);
        model.addAttribute("success", "Bid was created successfully.");
        model.addAttribute("bidList", bidListBusiness.getBidsList());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id
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
        model.addAttribute("success", "Bid successfully deleted.");
        model.addAttribute("bidList", bidListBusiness.getBidsList());
        return "redirect:/bidList/list";
    }
}

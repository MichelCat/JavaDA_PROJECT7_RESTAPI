package com.nnk.poseidon.controllers;

import com.nnk.poseidon.business.TradeBusiness;
import com.nnk.poseidon.domain.Trade;
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
 * TradeController is the Endpoint will perform the following actions via Get/Post/Put/Delete with HTTP on Trade.
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("trade")
public class TradeController {
    @Autowired
    private TradeBusiness tradeBusiness;

    /**
     * Read - Get the list of Trades.
     *
     * @param model Model object
     * @return View
     */
    @GetMapping("/list")
    public String home(Model model)
    {
        // Find all Trade, add to model
        log.debug("HTTP GET, display home form.");
        model.addAttribute("trades", tradeBusiness.getTradesList());
        return "trade/list";
    }

    /**
     * Read - Page add new Trade.
     *
     * @param trade The Trade object
     * @return View
     */
    @GetMapping("/add")
    public String addTradeForm(Trade trade) {
        log.debug("HTTP GET, display Trade add form.");
        return "trade/add";
    }

    /**
     * Create - Add new Trade
     *
     * @param trade The Trade object added
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PostMapping("/validate")
    public String validate(@Valid Trade trade
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check data valid and save to db, after saving return Trade list

        // Trade parameter is not valid
        if (result.hasErrors()) {
            log.debug("HTTP GET, Validation failed for Trade ({}).", trade);
            return "trade/add";
        }
        try {
            // Save Trade
            Trade tradeSave = tradeBusiness.createTrade(trade);
            log.info("HTTP GET, SUCCESSFUL ({}).", tradeSave);
            redirectAttributes.addFlashAttribute("success", "Trade was created successfully.");
            model.addAttribute("trades", tradeBusiness.getTradesList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/trade/list";
    }

    /**
     * Read - Page add new Trade.
     *
     * @param id Trade ID added
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     * @return View
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Get Trade by Id and to model then show to the form
        try {
            Trade trade = tradeBusiness.getTradeById(id);
            log.debug("HTTP GET, display Trade update form ({}).", trade);
            model.addAttribute("trade", trade);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/trade/list";
        }
        return "trade/update";
    }

    /**
     * Update - Update an existing Trade
     *
     * @param id Trade ID added
     * @param trade The Trade object updated
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PatchMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id
                            , @Valid Trade trade
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check required fields, if valid call service to update Trade and return Trade list

        // Trade ID parameter is null or zero
        if (id == null || id == 0) {
            log.debug(MessagePropertieFormat.getMessage("trade.error.template", id));
            model.addAttribute("errorMessage", MessagePropertieFormat.getMessage("trade.error.template", id));
            return "trade/update";
        }
        // Trade parameter is not valid
        if (result.hasErrors()) {
            log.debug("HTTP PATCH, Validation failed for Trade ({}).", trade);
            return "trade/update";
        }
        try {
            // Modify Trade
            Trade tradeSave = tradeBusiness.updateTrade(id, trade);
            log.info("HTTP PATCH, SUCCESSFUL ({}).", tradeSave);
            redirectAttributes.addFlashAttribute("success", "Trade was created successfully.");
            model.addAttribute("trades", tradeBusiness.getTradesList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/trade/list";
    }

    /**
     * Delete - Delete an Trade
     *
     * @param id Trade ID deleted
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Find Trade by Id and delete the Trade, return to Trade list

        // Trade ID parameter is null or zero
        if (id == null || id == 0) {
            log.debug(MessagePropertieFormat.getMessage("trade.error.template", id));
            redirectAttributes.addFlashAttribute("errorMessage", MessagePropertieFormat.getMessage("trade.error.template", id));
            return "redirect:/trade/list";
        }
        try {
            // Delete Trade
            tradeBusiness.deleteTrade(id);
            log.info("HTTP DELETE, SUCCESSFUL (Trade ID : {}).", id);
            redirectAttributes.addFlashAttribute("success", "Trade successfully deleted.");
            model.addAttribute("trades", tradeBusiness.getTradesList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/trade/list";
    }
}

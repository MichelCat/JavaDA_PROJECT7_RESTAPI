package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.TradeBusiness;
import com.nnk.poseidon.model.Trade;
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
    @Autowired
    private MessageSource messageSource;

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
        String msgSource = messageSource.getMessage("debug.trade.listForm"
                                , null, LocaleContextHolder.getLocale());
        log.debug("HTTP GET, " + msgSource);
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
        String msgSource = messageSource.getMessage("debug.trade.addForm"
                                , null, LocaleContextHolder.getLocale());
        log.debug("HTTP GET, " + msgSource);
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
            String msgSource = messageSource.getMessage("debug.trade.validation"
                                    , new Object[] { trade }
                                    , LocaleContextHolder.getLocale());
            log.debug("HTTP POST, " + msgSource);
            return "trade/add";
        }
        try {
            // Save Trade
            tradeBusiness.createTrade(trade);
            String msgSource = messageSource.getMessage("info.trade.created"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP POST, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
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
            String msgSource = messageSource.getMessage("debug.trade.updateForm"
                                    , null, LocaleContextHolder.getLocale());
            log.debug("HTTP GET, " + msgSource);
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

        // Trade parameter is not valid
        if (result.hasErrors()) {
            String msgSource = messageSource.getMessage("debug.trade.validation"
                                    , new Object[] { trade }
                                    , LocaleContextHolder.getLocale());
            log.debug("HTTP PATCH, " + msgSource);
            return "trade/update";
        }
        try {
            // Modify Trade
            tradeBusiness.updateTrade(id, trade);
            String msgSource = messageSource.getMessage("info.trade.updated"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP PATCH, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
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

        try {
            // Delete Trade
            tradeBusiness.deleteTrade(id);
            String msgSource = messageSource.getMessage("info.trade.deleted"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP DELETE, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/trade/list";
    }
}

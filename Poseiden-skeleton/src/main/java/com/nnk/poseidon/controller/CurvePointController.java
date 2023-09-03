package com.nnk.poseidon.controller;

import com.nnk.poseidon.business.CurvePointBusiness;
import com.nnk.poseidon.model.CurvePoint;
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
 * CurvePointController is the Endpoint will perform the following actions via Get/Post/Put/Delete with HTTP on CurvePoint.
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("curvePoint")
public class CurvePointController {
    @Autowired
    private CurvePointBusiness curvePointBusiness;
    @Autowired
    private MessageSource messageSource;

    /**
     * Read - Get the list of CurvePoints.
     *
     * @param model Model object
     * @return View
     */
    @GetMapping("/list")
    public String home(Model model)
    {
        // Find all Curve Point, add to model
        String msgSource = messageSource.getMessage("debug.curvePoint.listForm"
                                , null, LocaleContextHolder.getLocale());
        log.debug("HTTP GET, " + msgSource);
        model.addAttribute("curvePoints", curvePointBusiness.getCurvePointsList());
        return "curvePoint/list";
    }

    /**
     * Read - Page add new CurvePoint.
     *
     * @param curvePoint The CurvePoint object
     * @return View
     */
    @GetMapping("/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        String msgSource = messageSource.getMessage("debug.curvePoint.addForm"
                                , null, LocaleContextHolder.getLocale());
        log.debug("HTTP GET, " + msgSource);
        return "curvePoint/add";
    }

    /**
     * Create - Add new CurvePoint
     *
     * @param curvePoint The CurvePoint object added
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PostMapping("/validate")
    public String validate(@Valid CurvePoint curvePoint
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check data valid and save to db, after saving return Curve list

        // CurvePoint parameter is not valid
        if (result.hasErrors()) {
            String msgSource = messageSource.getMessage("debug.curvePoint.validation"
                                    , new Object[] { curvePoint }
                                    , LocaleContextHolder.getLocale());
            log.debug("HTTP POST, " + msgSource);
            return "curvePoint/add";
        }
        try {
            // Save CurvePoint
            curvePointBusiness.createCurvePoint(curvePoint);
            String msgSource = messageSource.getMessage("info.curvePoint.created"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP POST, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
            model.addAttribute("curvePoints", curvePointBusiness.getCurvePointsList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/curvePoint/list";
    }

    /**
     * Read - Page add new CurvePoint.
     *
     * @param id CurvePoint ID added
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     * @return View
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id
                                , Model model
                                , RedirectAttributes redirectAttributes) {
        // Get CurvePoint by Id and to model then show to the form
        try {
            CurvePoint curvePoint = curvePointBusiness.getCurvePointById(id);
            String msgSource = messageSource.getMessage("debug.curvePoint.updateForm"
                                    , null, LocaleContextHolder.getLocale());
            log.debug("HTTP GET, " + msgSource);
            model.addAttribute("curvePoint", curvePoint);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/update";
    }

    /**
     * Update - Update an existing CurvePoint
     *
     * @param id CurvePoint ID added
     * @param curvePoint The CurvePoint object updated
     * @param result Result of a validation
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @PatchMapping("/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id
                            , @Valid CurvePoint curvePoint
                            , BindingResult result
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Check required fields, if valid call service to update Curve and return Curve list

        // CurvePoint parameter is not valid
        if (result.hasErrors()) {
            String msgSource = messageSource.getMessage("debug.curvePoint.validation"
                                    , new Object[] { curvePoint }
                                    , LocaleContextHolder.getLocale());
            log.debug("HTTP PATCH, " + msgSource);
            return "curvePoint/update";
        }
        try {
            // Modify CurvePoint
            curvePointBusiness.updateCurvePoint(id, curvePoint);
            String msgSource = messageSource.getMessage("info.curvePoint.updated"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP PATCH, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
            model.addAttribute("curvePoints", curvePointBusiness.getCurvePointsList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/curvePoint/list";
    }

    /**
     * Delete - Delete an CurvePoint
     *
     * @param id CurvePoint ID deleted
     * @param model Model object
     * @param redirectAttributes RedirectAttributes object
     *
     * @return View
     */
    @GetMapping("/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id
                            , Model model
                            , RedirectAttributes redirectAttributes) {
        // Find Curve by Id and delete the Curve, return to Curve list

        try {
            // Delete CurvePoint
            curvePointBusiness.deleteCurvePoint(id);
            String msgSource = messageSource.getMessage("info.curvePoint.deleted"
                                    , null, LocaleContextHolder.getLocale());
            log.info("HTTP DELETE, " + msgSource);
            redirectAttributes.addFlashAttribute("successMessage", msgSource);
            model.addAttribute("curvePoints", curvePointBusiness.getCurvePointsList());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/curvePoint/list";
    }
}

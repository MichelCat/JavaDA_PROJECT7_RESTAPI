package com.nnk.poseidon.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController is the Endpoint of for admin login page
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Controller
public class HomeController
{

	@Autowired
	private MessageSource messageSource;

	/**
	 * Read - Get home Page for ADMIN
	 *
	 * @param model Model object
	 * @return View
	 */
	@GetMapping("/home/admin")
	public String home(Model model)
	{
		String msgSource = messageSource.getMessage("debug.home.admin"
								, null, LocaleContextHolder.getLocale());
		log.debug("HTTP GET, " + msgSource);
		return "home";
	}

	/**
	 * Read - Get home Page for USER
	 *
	 * @param model Model object
	 * @return View
	 */
	@GetMapping("/home/user")
	public String adminHome(Model model)
	{
		String msgSource = messageSource.getMessage("debug.home.user"
								, null, LocaleContextHolder.getLocale());
		log.debug("HTTP GET, " + msgSource);
		return "redirect:/bidList/list";
	}
}

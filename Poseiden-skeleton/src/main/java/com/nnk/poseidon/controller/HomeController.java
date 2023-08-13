package com.nnk.poseidon.controller;

import lombok.extern.slf4j.Slf4j;
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
	/**
	 * Read - Get home Page for ADMIN
	 *
	 * @param model Model object
	 * @return View
	 */
	@GetMapping("/admin/home")
	public String home(Model model)
	{
		log.debug("HTTP GET, display home Page for ADMIN.");
		return "home";
	}

	/**
	 * Read - Get home Page for USER
	 *
	 * @param model Model object
	 * @return View
	 */
	@GetMapping("/user/home")
	public String adminHome(Model model)
	{
		log.debug("HTTP GET, display home Page for USER.");
		return "redirect:/bidList/list";
	}
}

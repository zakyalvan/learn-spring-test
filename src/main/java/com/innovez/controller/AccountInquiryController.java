package com.innovez.controller;

import java.awt.print.Pageable;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/accounts")
public class AccountInquiryController {
	private Logger logger = Logger.getLogger(AccountInquiryController.class);
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(Pageable pageable, Model model) {
		logger.debug("Show accounts list.");
		return "accounts/list";
	}
}

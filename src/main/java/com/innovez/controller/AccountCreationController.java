package com.innovez.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/accounts")
public class AccountCreationController {
	@RequestMapping(method=RequestMethod.POST)
	public String create() {
		return "redirect:/accounts";
	}
}

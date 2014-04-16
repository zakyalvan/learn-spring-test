package com.innovez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.innovez.entity.Account;
import com.innovez.entity.Money;
import com.innovez.service.TransferException;
import com.innovez.service.TransferService;
import com.innovez.service.TransferService.TransferStatus;

/**
 * Controller for handling user's transfer request from browser.
 * 
 * @author zakyalvan
 */
@Controller
@RequestMapping(value="/transfers")
public class TransferController {
	private Logger logger = Logger.getLogger(TransferController.class);
	
	private TransferService transferService;
	
	TestExecutionListener test;
	/**
	 * Attach form backing object or command to model.
	 * 
	 * @return
	 */
	@ModelAttribute("form")
	public TransferForm createFormBacking() {
		logger.debug("Create form.");
		return new TransferForm();
	}
	
	/**
	 * List owned account of logged-in user.
	 * 
	 * @return
	 */
	@ModelAttribute("accounts")
	public List<Account> listOwnedAccount() {
		logger.debug("List owned account.");
		return new ArrayList<Account>();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody TransferStatus processTransfer(@ModelAttribute TransferForm form, BindingResult bindingResult) {
		logger.debug("Perform transfer with form/command : " + form);
		return transferService.transfer(form.getFromAccount(), form.getToAccount(), new Money(form.getCurrency(), form.getAmount()));
	}
	
	@ExceptionHandler(TransferException.class)
	public @ResponseBody String handleException(TransferException transferException) {
		return "Failed due to : " + transferException.getMessage();
	}
	
	@Autowired
	public void setTransferService(TransferService transferService) {
		this.transferService = transferService;
	}

	/**
	 * Form backing object or command for tranfer request.
	 */
	@SuppressWarnings("serial")
	public static class TransferForm implements Serializable {
		@NotNull
		public String fromAccount;
		@NotNull
		public String toAccount;
		@NotNull
		public String currency;
		@NotNull
		public Double amount;
		
		public String getFromAccount() {
			return fromAccount;
		}
		public void setFromAccount(String fromAccount) {
			this.fromAccount = fromAccount;
		}
		
		public String getToAccount() {
			return toAccount;
		}
		public void setToAccount(String toAccount) {
			this.toAccount = toAccount;
		}
		
		
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		
		public Double getAmount() {
			return amount;
		}
		public void setAmount(Double amount) {
			this.amount = amount;
		}
		
		@Override
		public String toString() {
			return "TransferForm [fromAccount=" + fromAccount + ", toAccount="
					+ toAccount + ", currency=" + currency + ", amount="
					+ amount + "]";
		}
	}
}

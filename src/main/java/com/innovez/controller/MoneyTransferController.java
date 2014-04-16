package com.innovez.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.innovez.service.dto.TransferCommand;

/**
 * Controller for handling money transfer, from one account to another account.
 * 
 * @author zakyalvan
 */
@Controller
@RequestMapping(value="/transfers")
public class MoneyTransferController {
	private Logger logger = Logger.getLogger(MoneyTransferController.class);
	
	/**
	 * Transfer service.
	 */
	private TransferService transferService;
	
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
	public @ResponseBody TransferStatus processTransfer(@Valid @ModelAttribute TransferForm form, BindingResult bindingResult) {
		logger.debug("Perform transfer with form/command : " + form);
		if(bindingResult.hasErrors()) {
			return null;
		}
		return transferService.transfer(form);
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
	public static class TransferForm implements TransferCommand {
		@NotNull
		private String sourceAccount;
		@NotNull
		private String targetAccount;
		@NotNull
		private String currency;
		@NotNull
		private Double amount;
		@NotNull
		private final Source source = Source.INTERNET;
		
		@Override
		public String getSourceAccount() {
			return sourceAccount;
		}
		public void setSourceAccount(String sourceAccount) {
			this.sourceAccount = sourceAccount;
		}

		@Override
		public String getTargetAccount() {
			return targetAccount;
		}
		public void setTargetAccount(String targetAccount) {
			this.targetAccount = targetAccount;
		}

		@Override
		public Money getTransferAmount() {
			return new Money(currency, amount);
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
		public Source getSource() {
			return source;
		}
		@Override
		public String toString() {
			return "TransferForm [sourceAccount=" + sourceAccount
					+ ", targetAccount=" + targetAccount + ", currency="
					+ currency + ", amount=" + amount + ", source=" + source
					+ "]";
		}
	}
}

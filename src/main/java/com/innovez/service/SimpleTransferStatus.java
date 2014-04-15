package com.innovez.service;

import java.io.Serializable;
import java.util.Date;

import com.innovez.entity.Money;
import com.innovez.service.TransferService.TransferStatus;

@SuppressWarnings("serial")
public class SimpleTransferStatus implements TransferStatus, Serializable {
	private String fromAccountNumber;
	private String fromAccountOwner;
	private String toAccountNumber;
	private String toAccountOwner;
	private Money transferAmount;
	
	private Date completedDate = new Date();
	
	public SimpleTransferStatus(String fromAccountNumber, String fromAccountOwner, String toAccountNumber, String toAccountOwner, Money transferAmount) {
		this.fromAccountNumber = fromAccountNumber;
		this.fromAccountOwner = fromAccountOwner;
		this.toAccountNumber = toAccountNumber;
		this.toAccountOwner = toAccountOwner;
		this.transferAmount = transferAmount;
	}
	
	@Override
	public String getFromAccountNumber() {
		return fromAccountNumber;
	}
	@Override
	public String getFromAccountOwner() {
		return fromAccountOwner;
	}
	@Override
	public String getToAccountNumber() {
		return toAccountNumber;
	}
	@Override
	public String getToAccountOwner() {
		return toAccountOwner;
	}
	@Override
	public Money getTransferAmount() {
		return transferAmount;
	}
	
	@Override
	public Date getCompletedDate() {
		return completedDate;
	}
}

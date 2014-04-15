package com.innovez.service;

import java.util.Date;

import com.innovez.entity.Money;

/**
 * Contract for money transfer service.
 * 
 * @author zakyalvan
 *
 */
public interface TransferService {
	TransferStatus transfer(String fromAccountNumber, String toAccountNumber, Money amount) throws TransferException;
	
	public static interface TransferStatus {
		String getFromAccountNumber();
		String getFromAccountOwner();
		String getToAccountNumber();
		String getToAccountOwner();
		
		Money getTransferAmount();
		
		Date getCompletedDate();
	}
	
	public static interface ServiceFeePolicy {
		
	}
}
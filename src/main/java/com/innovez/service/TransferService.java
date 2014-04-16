package com.innovez.service;

import java.util.Date;

import com.innovez.entity.Account;
import com.innovez.entity.Money;
import com.innovez.service.dto.TransferCommand;

/**
 * Contract for money transfer service.
 * 
 * @author zakyalvan
 *
 */
public interface TransferService {
	/**
	 * Check whether we can transfer money with given parameter.
	 * 
	 * @return
	 */
	TransferStatus checkTransfer(String fromAccountNumber, String toAccountNumber, Money transferingAmount);
	
	/**
	 * Do transfering money.
	 * 
	 * @param fromAccountNumber
	 * @param toAccountNumber
	 * @param amount
	 * @return
	 * @throws TransferException
	 */
	TransferStatus transfer(TransferCommand command) throws TransferException;
	
	/**
	 * Dto for reposrting transfer status.
	 * 
	 * @author zakyalvan
	 */
	public static interface TransferStatus {
		String getFromAccountNumber();
		String getFromAccountOwner();
		String getToAccountNumber();
		String getToAccountOwner();
		
		Money getTransferAmount();
		
		Date getCompletedDate();
	}
}
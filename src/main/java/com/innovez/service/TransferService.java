package com.innovez.service;

import java.util.Date;

import com.innovez.entity.Account;
import com.innovez.entity.Money;

/**
 * Contract for money transfer service.
 * 
 * @author zakyalvan
 *
 */
public interface TransferService {
	/**
	 * Do transfering money.
	 * 
	 * @param fromAccountNumber
	 * @param toAccountNumber
	 * @param amount
	 * @return
	 * @throws TransferException
	 */
	TransferStatus transfer(String fromAccountNumber, String toAccountNumber, Money amount) throws TransferException;
	
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
	
	/**
	 * Strategy object to enforce transfer service fee policy.
	 * 
	 * @author zakyalvan
	 */
	public static interface TransferFeePolicy {
		Money calculateFee(Account fromAccount, Account account, Money transfered);
	}
}
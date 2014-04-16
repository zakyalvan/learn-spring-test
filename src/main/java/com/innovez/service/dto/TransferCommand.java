package com.innovez.service.dto;

import java.io.Serializable;

import com.innovez.entity.Money;

/**
 * Transfer command.
 * 
 * @author zakyalvan
 */
public interface TransferCommand extends Serializable {
	/**
	 * Retrieve source account number.
	 * 
	 * @return
	 */
	String getSourceAccount();
	
	/**
	 * Retrieve target account number.
	 * 
	 * @return
	 */
	String getTargetAccount();
	
	/**
	 * Retrieve transfered amount.
	 * 
	 * @return
	 */
	Money getTransferAmount();
	
	/**
	 * Retrieve source command.
	 * 
	 * @return
	 */
	Source getSource();
	
	public static enum Source {
		INTERNET, SMS, ATM
	}
}

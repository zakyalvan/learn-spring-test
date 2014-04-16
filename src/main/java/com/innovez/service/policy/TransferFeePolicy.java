package com.innovez.service.policy;

import com.innovez.entity.Money;
import com.innovez.service.dto.TransferCommand;

/**
 * Strategy object to enforce transfer service fee policy.
 * 
 * @author zakyalvan
 */
public interface TransferFeePolicy {
	/**
	 * Calculate fee for each transfer transaction.
	 * 
	 * @param command
	 * @return
	 */
	Money calculateFee(TransferCommand command);
}
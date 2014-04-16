package com.innovez.service.policy;

import org.springframework.stereotype.Component;

import com.innovez.entity.Money;
import com.innovez.service.dto.TransferCommand;

@Component("SGD")
public class SgdTransferFeePolicy implements TransferFeePolicy {
	/**
	 * We charge SGD 1 for each transfer transaction.
	 */
	@Override
	public Money calculateFee(TransferCommand command) {
		return new Money("SGD", 1d);
	}
}
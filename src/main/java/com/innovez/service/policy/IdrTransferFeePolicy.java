package com.innovez.service.policy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.innovez.entity.Money;
import com.innovez.service.dto.TransferCommand;

@Component("IDR")
@Qualifier("IDR")
public class IdrTransferFeePolicy implements TransferFeePolicy {
	/**
	 * For each transfer transaction from IDR account, we charge the IDR 3000 per-transaction.
	 */
	@Override
	public Money calculateFee(TransferCommand command) {
		return new Money("IDR", 3000d);
	}
}
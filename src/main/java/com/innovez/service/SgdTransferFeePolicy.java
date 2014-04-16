package com.innovez.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.innovez.entity.Account;
import com.innovez.entity.Money;
import com.innovez.service.TransferService.TransferFeePolicy;

@Component("SGD")
@Qualifier("SGD")
public class SgdTransferFeePolicy implements TransferFeePolicy {
	@Override
	public Money calculateFee(Account fromAccount, Account account, Money transfered) {
		return transfered;
	}
}
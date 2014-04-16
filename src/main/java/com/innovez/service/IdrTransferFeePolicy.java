package com.innovez.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.innovez.entity.Account;
import com.innovez.entity.Money;
import com.innovez.service.TransferService.TransferFeePolicy;

@Component("IDR")
@Qualifier("IDR")
public class IdrTransferFeePolicy implements TransferFeePolicy {
	@Override
	public Money calculateFee(Account fromAccount, Account account, Money transfered) {
		return transfered;
	}
}

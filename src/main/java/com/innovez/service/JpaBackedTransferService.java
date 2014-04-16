package com.innovez.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.innovez.entity.Account;
import com.innovez.entity.Account.Status;
import com.innovez.entity.Money;
import com.innovez.repo.AccountRepository;
import com.innovez.service.dto.TransferCommand;
import com.innovez.service.policy.TransferFeePolicy;

/**
 * Default implementation of {@link TransferService}
 * 
 * @author zakyalvan
 *
 */
@Service
public class JpaBackedTransferService implements TransferService {
	private Logger logger = Logger.getLogger(JpaBackedTransferService.class);
	
	private AccountRepository accountRepository;
	
	private Map<String, TransferFeePolicy> transferFeePoliciesMap;
	
	@Override
	@Transactional(readOnly=true)
	public TransferStatus checkTransfer(String fromAccountNumber, String toAccountNumber, Money transferingAmount) {
		return null;
	}

	/**
	 * Currently, no check for currency.
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public TransferStatus transfer(TransferCommand command) throws TransferException {
		logger.debug("Perform transfer.");
		if(!(accountRepository.exists(command.getSourceAccount()) && accountRepository.exists(command.getTargetAccount()))) {
			throw new TransferException.UnregisteredAccountException("Unregistered account used on tranfer");
		}
		
		Account sourceAccount = accountRepository.findOne(command.getSourceAccount());
		Account targetAccount = accountRepository.findOne(command.getTargetAccount());
		
		logger.debug("Check whether accounts suspended.");
		/**
		 * TODO We shall re-factor this, add custom query method on repository 
		 * to check whether fromAccount and toAccount is'n in SUSPENDED state, instead of check manually like this.
		 * Remember tell-don't-ask (TDA) principle.
		 * 
		 * Also we can simply use the query method in, for example, validator class.
		 */
		if(sourceAccount.getStatus() == Status.SUSPENDED || targetAccount.getStatus() == Status.SUSPENDED) {
			throw new TransferException.SuspendedAccountUsedException("Suspended account.");
		}
		
		logger.debug("Check whether source account has sufficient balance to transfer.");
		/**
		 * TODO  We shall re-factor this, add custom query method on repository 
		 * to check whether fromAccount have sufficient account, instead of check manually like this.
		 * Remember tell-don't-ask (TDA) principle.
		 */
		TransferFeePolicy transferFeePolicy = transferFeePoliciesMap.get("IDR");
		Double minimalRequiredBalanceAmount = command.getTransferAmount().getAmount() + transferFeePolicy.calculateFee(command).getAmount();
		
		// Checking double-precision object like this is not safe. This for simplicity purpose.
		if(sourceAccount.getBalance().getAmount() <= minimalRequiredBalanceAmount) {
			throw new TransferException.UnsufficientBalanceException("Unsufficient account balance.");
		}
		
		// Now, we have no problems to transfer the money.
		sourceAccount.getBalance().substract(command.getTransferAmount()).substract(transferFeePolicy.calculateFee(command));
		targetAccount.getBalance().add(command.getTransferAmount());
		
		sourceAccount = accountRepository.save(sourceAccount);
		targetAccount = accountRepository.save(targetAccount);
		
		return new SimpleTransferStatus(sourceAccount.getNumber(), sourceAccount.getOwner().getName(), targetAccount.getNumber(), targetAccount.getOwner().getName(), command.getTransferAmount());
	}

	@Autowired
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Autowired
	public void setTransferFeePoliciesMap(Map<String, TransferFeePolicy> transferFeePoliciesMap) {
		this.transferFeePoliciesMap = transferFeePoliciesMap;
	}
	
}

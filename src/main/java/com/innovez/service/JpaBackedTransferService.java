package com.innovez.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.innovez.entity.Account;
import com.innovez.entity.Account.Status;
import com.innovez.entity.Money;
import com.innovez.repo.AccountRepository;

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
	
	/**
	 * Currently, no check for currency.
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public TransferStatus transfer(String fromAccountNumber, String toAccountNumber, Money amount) throws TransferException {
		logger.debug("Perform transfer.");
		if(!(accountRepository.exists(fromAccountNumber) && accountRepository.exists(toAccountNumber))) {
			throw new TransferException.UnregisteredAccountException("Unregistered account used on tranfer");
		}
		
		Account fromAccount = accountRepository.findOne(fromAccountNumber);
		Account toAccount = accountRepository.findOne(toAccountNumber);
		
		logger.debug("Check whether accounts suspended.");
		/**
		 * TODO We shall refactor this, add custom query method on repository 
		 * to check whether fromAccount and toAccount is'n in SUSPENDED state, instead of check manually like this.
		 * Remember tell-don't-ask (TDA) principle.
		 * 
		 * Also we can simply use the query method in, for example, validator class.
		 */
		if(fromAccount.getStatus() == Status.SUSPENDED || toAccount.getStatus() == Status.SUSPENDED) {
			throw new TransferException.SuspendedAccountUsedException("Suspended account.");
		}
		
		logger.debug("Check whether has sufficient balance to transfer.");
		/**
		 * TODO  We shall refactor this, add custom query method on repository 
		 * to check whether fromAccount have sufficient account, instead of check manually like this.
		 * Remember tell-don't-ask (TDA) principle.
		 */
		// Checking double-precision object like this is not safe. This for simplicity purpose.
		if(fromAccount.getBalance().getAmount() <= amount.getAmount()) {
			throw new TransferException.UnsufficientBalanceException("Unsufficient account balance.");
		}
		
		// Now, we have no problems to transfer the money.
		fromAccount.getBalance().decreaseAmount(amount.getAmount());
		toAccount.getBalance().increaseAmount(amount.getAmount());
		
		fromAccount = accountRepository.save(fromAccount);
		toAccount = accountRepository.save(toAccount);
		
		return new SimpleTransferStatus(fromAccount.getNumber(), fromAccount.getOwner().getName(), toAccount.getNumber(), toAccount.getOwner().getName(), amount);
	}

	@Autowired
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
}

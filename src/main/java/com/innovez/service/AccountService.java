package com.innovez.service;

import java.util.List;

import com.innovez.entity.Account;

/**
 * Contract for account service.
 * 
 * @author zakyalvan
 */
public interface AccountService {
	/**
	 * List all account of currently logged in user.
	 * 
	 * @return
	 */
	List<Account> listAllAccounts();
}

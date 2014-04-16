package com.innovez.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innovez.entity.Account;
import com.innovez.repo.AccountRepository;

@Service
public class JpaBackedAccountService implements AccountService {
	private AccountRepository accountRepository;

	@Override
	public List<Account> listAllAccounts() {
		return accountRepository.findAll();
	}

	@Autowired
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
}

package com.innovez.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovez.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

}

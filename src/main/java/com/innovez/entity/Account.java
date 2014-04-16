package com.innovez.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="innvz_account")
public class Account {
	@Id
	@Column(name="account_number")
	private String number;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="owner_email")
	private Person owner;
	
	@NotNull
	@Embedded
	private Money balance;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="account_status")
	private Status status;
	
	@Valid
	@Size(min=1)
	@OrderBy(value="timestamp")
	@ElementCollection(fetch=FetchType.LAZY)
	private List<BalanceHistory> histories = new ArrayList<Account.BalanceHistory>();
	
	@Version
	@Column(name="record_version")
	private Integer version;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	public Person getOwner() {
		return owner;
	}
	public void setOwner(Person owner) {
		this.owner = owner;
	}
	
	public Money getBalance() {
		return balance;
	}
	public void setBalance(Money balance) {
		this.balance = balance;
	}
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public List<BalanceHistory> getHistories() {
		return histories;
	}
	public void setHistories(List<BalanceHistory> histories) {
		this.histories = histories;
	}

	/**
	 * Enum for status of Account
	 */
	public static enum Status {
		ACTIVE, SUSPENDED
	}
	
	/**
	 * Embedded object recording balance history.
	 */
	@Embeddable
	public static class BalanceHistory {
		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="account_number")
		private Account account;
		
		@NotNull
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name="timestamp")
		private Date timestamp;
		
		@NotNull
		@Embedded
		private Money amount;
		
		private boolean increased;
	}
}

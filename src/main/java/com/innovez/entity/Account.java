package com.innovez.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

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

	public static enum Status {
		ACTIVE, SUSPENDED
	}
}

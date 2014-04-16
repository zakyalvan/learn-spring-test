package com.innovez.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

@Embeddable
public class Money {
	@NotNull
	@Column(name="currency_code")
	private String currencyCode;
	
	@NotNull
	@Min(0)
	private Double amount = 0d;

	public Money() {}
	public Money(String currencyCode, Double amount) {
		this.currencyCode = currencyCode;
		this.amount = amount;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public Double getAmount() {
		return amount;
	}
	void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Money add(Money money) {
		Assert.isTrue(currencyCode.equalsIgnoreCase(money.getCurrencyCode()), "Can't add money with different currency.");
		Assert.isTrue(money.getAmount() > 0, "Added money amount should should greater than zero.");
		
		this.amount += money.getAmount();
		return this;
	}
	public Money substract(Money money) {
		Assert.isTrue(currencyCode.equalsIgnoreCase(money.getCurrencyCode()), "Can't substract money with different currency.");
		Assert.isTrue(money.getAmount() > 0, "Substracted money amount should should greater than zero.");
		
		this.amount -= money.getAmount();
		return this;
	}
}

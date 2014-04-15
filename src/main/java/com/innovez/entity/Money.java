package com.innovez.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
	public Double getAmount() {
		return amount;
	}
	
	public void increaseAmount(Double amountToAdd) {
		this.amount += amountToAdd;
	}
	public void decreaseAmount(Double amountTuSub) {
		this.amount -= amountTuSub;
	}
}

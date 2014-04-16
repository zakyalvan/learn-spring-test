package com.innovez.service;

import com.innovez.entity.Money;

/**
 * Contract for exchange service.
 * 
 * @author zakyalvan
 */
public interface ExchangeService {
	/**
	 * Retrieve current exchange rate.
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	Money getRate(String sourceCurrency, String targetCurrency);
	
	/**
	 * Calculate exchange base on current rate.
	 * 
	 * @param source
	 * @param toCurrency
	 * @return
	 * @throws ExchangeException
	 */
	Money calculate(Money source, String targetCurrency) throws ExchangeException;
}

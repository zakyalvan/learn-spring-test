package com.innovez.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.innovez.entity.Money;

@Service
public class SimpleExchangeService implements ExchangeService {
	private Map<String, Money> ratesMap = new HashMap<String, Money>();
	
	public void setRatesMap(Map<String, Money> ratesMap) {
		this.ratesMap = ratesMap;
	}

	@Override
	public Money getRate(String sourceCurrency, String targetCurrency) {
		return null;
	}

	@Override
	public Money calculate(Money source, String targetCurrency) throws ExchangeException {
		return null;
	}
}

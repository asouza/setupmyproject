package com.setupmyproject.components;

import java.math.BigDecimal;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;

@Component
public class PriceDisplayer {
	
	public final static CurrencyUnit currencyUnit = MonetaryCurrencies.getCurrency(Locale.US);
	private static MonetaryAmountFormat amountFormat = MonetaryFormats.getAmountFormat(Locale.US);

	/**
	 * 
	 * @param value valor que deve ser formatado
	 * @param name nome que deve ser exibido perto do valor
	 * @return name(5.00USD)
	 */
	public String show(BigDecimal value, String name){
		return name+"("+show(value)+")";
	}
	
	public String show(BigDecimal value){
		Money money = Money.of(value, currencyUnit);
		return amountFormat.format(money);
	}
	
	
	
}

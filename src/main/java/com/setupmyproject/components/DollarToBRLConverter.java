package com.setupmyproject.components;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

@Component
public class DollarToBRLConverter {

	public BigDecimal convert(BigDecimal value){
		return value.multiply(new BigDecimal("2.5")).setScale(2,RoundingMode.HALF_UP);
	}
}

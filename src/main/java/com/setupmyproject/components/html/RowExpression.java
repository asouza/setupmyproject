package com.setupmyproject.components.html;

import java.util.ArrayList;
import java.util.List;

public class RowExpression extends ViewElement{

	private List<String> valueExpressions = new ArrayList<>();

	/**
	 * 
	 * @param valueExpression expressao que deve ser envolvida com a expression language em questao. Ex: user.name
	 * @return
	 */
	public RowExpression td(String valueExpression) {
		this.valueExpressions.add(valueExpression);
		return this;
	}
	
	public List<String> getValueExpressions() {
		return valueExpressions;
	}

}

package com.setupmyproject.components.html.jstl;

import java.util.ArrayList;
import java.util.List;

import com.setupmyproject.components.html.ViewElement;

public class JSTLForeachElement extends ViewElement{

	private String itemsExpression;
	private String varName;
	private List<ViewElement> innerElements = new ArrayList<ViewElement>();

	/**
	 * 
	 * @param itemsExpression expressao para passar no atributo items. Ex:${products}
	 * @param varName nome da variavel para usar no loop
	 */
	public JSTLForeachElement(String itemsExpression, String varName) {
		this.itemsExpression = itemsExpression;
		this.varName = varName;
	}
	
	public String getItemsExpression() {
		return itemsExpression;
	}
	
	public String getVarName() {
		return varName;
	}

	public JSTLForeachElement insertElement(ViewElement viewElement) {
		this.innerElements.add(viewElement);
		return this;
	}

	@Override
	public boolean isGroup() {
		return true;
	}
	
	@Override
	public List<ViewElement> innerElements() {
		return innerElements;
	}
}

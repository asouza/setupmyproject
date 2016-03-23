package com.setupmyproject.components.html;

/**
 * Representa um RadioButton no html
 * @author alberto
 *
 */
public class RadioButtonHtmlElement extends GenericInputFormElement{

	private String name;

	public RadioButtonHtmlElement(String name) {
		this.name = name;		
	}

	@Override
	public String getName() {
		return this.name;
	}

}

package com.setupmyproject.components.html;

/**
 * Representa um checkbox na página
 * @author alberto
 *
 */
public class CheckBoxHtmlElement extends GenericInputFormElement {

	private String name;

	public CheckBoxHtmlElement(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}

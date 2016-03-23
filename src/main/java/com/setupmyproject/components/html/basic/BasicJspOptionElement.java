package com.setupmyproject.components.html.basic;

import com.setupmyproject.components.html.ViewElement;

public class BasicJspOptionElement extends ViewElement {

	private String valuePath;
	private String labelPath;
	private String valueToBeComparedPath;

	/**
	 * 
	 * @param valuePath
	 *            path pare ser usado para recuperar o valor do value do select.
	 *            Ex: category.id
	 * @param labelPath
	 *            para para ser usado para recuperar a label que aparece no
	 *            select. Ex: category.name
	 * @param valueToBeComparedPath
	 *            para para ser usado para saber se a option é selecionada ou
	 *            não
	 */
	public BasicJspOptionElement(String valuePath, String labelPath,
			String valueToBeComparedPath) {
		this.valuePath = valuePath;
		this.labelPath = labelPath;
		this.valueToBeComparedPath = valueToBeComparedPath;
	}

	public String getValuePath() {
		return valuePath;
	}

	public String getLabelPath() {
		return labelPath;
	}

	public String getValueToBeComparedPath() {
		return valueToBeComparedPath;
	}

}

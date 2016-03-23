package com.setupmyproject.components.html;

public class InputTextHtmlElement extends GenericInputFormElement {

	private String name;
	private String inputType;

	public InputTextHtmlElement(String name) {
		this(name,"text");
	}

	public InputTextHtmlElement(String name, String inputType) {
		this.name = name;
		this.inputType = inputType;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InputTextHtmlElement other = (InputTextHtmlElement) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public String getInputType() {
		return inputType;
	}
}

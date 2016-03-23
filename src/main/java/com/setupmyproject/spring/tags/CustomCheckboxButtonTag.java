package com.setupmyproject.spring.tags;

public class CustomCheckboxButtonTag extends CustomMulticheckedInputTag{
	
	@Override
	protected String getInputType() {
		return "checkbox";
	}

}

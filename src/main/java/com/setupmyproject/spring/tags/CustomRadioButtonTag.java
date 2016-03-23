package com.setupmyproject.spring.tags;

public class CustomRadioButtonTag extends CustomMulticheckedInputTag{

	@Override
	protected String getInputType() {
		return "radio";
	}
}

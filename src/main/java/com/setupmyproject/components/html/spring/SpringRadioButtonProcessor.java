package com.setupmyproject.components.html.spring;

import org.springframework.util.StringUtils;

import com.setupmyproject.components.html.RadioButtonHtmlElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;

public class SpringRadioButtonProcessor implements ViewPartProcessor {

	@Override
	public boolean supports(ViewElement viewElement) {
		return viewElement.getClass().equals(RadioButtonHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement viewElement) {
		RadioButtonHtmlElement radio = (RadioButtonHtmlElement) viewElement;
		StringBuilder radios = new StringBuilder();
		radios.append("<form:radiobutton path="
				+ StringUtils.quote(radio.getName())
				+ " value='true' label='Yes'/>");
		radios.append("<form:radiobutton path="
				+ StringUtils.quote(radio.getName())
				+ " value='false' label='No'/>");
		return radios.toString();
	}

}

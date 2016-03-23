package com.setupmyproject.components.html.spring;

import org.springframework.util.StringUtils;

import com.setupmyproject.components.html.CheckBoxHtmlElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;

public class SpringCheckboxProcessor implements ViewPartProcessor {

	@Override
	public boolean supports(ViewElement viewElement) {
		return viewElement.getClass().equals(CheckBoxHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement viewElement) {
		CheckBoxHtmlElement checkbox = (CheckBoxHtmlElement) viewElement;
		StringBuilder inputs = new StringBuilder();
		inputs.append("<form:checkbox path="
				+ StringUtils.quote(checkbox.getName())
				+ " value='true' label='Yes'/>");
		return inputs.toString();
	}

}

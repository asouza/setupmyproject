package com.setupmyproject.components.html.spring;

import com.setupmyproject.components.html.InputTextHtmlElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;

public class SpringInputTypeProcessor implements ViewPartProcessor {

	@Override
	public boolean supports(ViewElement htmlElement) {
		return htmlElement.getClass().equals(InputTextHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement htmlElement) {
		InputTextHtmlElement inputTextHtmlElement = (InputTextHtmlElement) htmlElement;
		String path = inputTextHtmlElement.getName();
		return "<form:input path='"+path+"' type='"+inputTextHtmlElement.getInputType()+"'/>\n"
				+ "<form:errors path='"+path+"'/>\n";
	}

}

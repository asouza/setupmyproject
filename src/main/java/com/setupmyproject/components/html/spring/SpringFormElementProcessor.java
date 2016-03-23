package com.setupmyproject.components.html.spring;

import org.springframework.util.Assert;

import com.setupmyproject.components.html.FormHtmlElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;

public class SpringFormElementProcessor implements ViewPartProcessor {
	

	public static final String COMMAND_NAME_ATTRIBUTE = "commandName";

	@Override
	public boolean supports(ViewElement htmlElement) {
		return htmlElement.getClass().equals(FormHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement htmlElement) {
		FormHtmlElement formElement = (FormHtmlElement) htmlElement;
		Assert.isTrue(formElement.metaData().containsKey(COMMAND_NAME_ATTRIBUTE), "Você precisa definir um commandName para o form do spring");
		return "<form:form name='" + formElement.getName() + "' servletRelativeAction='"
				+ formElement.getAction() + "' method='"
				+ formElement.getMethod() + "' commandName='"+formElement.metaData().get(COMMAND_NAME_ATTRIBUTE)+"'>\n";
	}
	
	@Override
	public String processClose(ViewElement htmlElement) {
		return "</form:form>\n";
	}

}

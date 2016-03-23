package com.setupmyproject.components.html.spring;

import com.setupmyproject.components.html.SubmitHtmlElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;

public class SpringSubmitButtonProcessor implements ViewPartProcessor {

	@Override
	public boolean supports(ViewElement htmlElement) {
		return htmlElement.getClass().equals(SubmitHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement htmlElement) {
		SubmitHtmlElement submitHtmlElement = (SubmitHtmlElement) htmlElement;
		return "<input type='submit' name='"+submitHtmlElement.getName()+"' value='"+submitHtmlElement.getValue()+"'/>\n";
	}

}

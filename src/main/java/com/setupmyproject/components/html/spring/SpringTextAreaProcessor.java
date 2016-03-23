package com.setupmyproject.components.html.spring;

import org.springframework.util.StringUtils;

import com.setupmyproject.components.html.TextAreaHtmlElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;

public class SpringTextAreaProcessor implements ViewPartProcessor {

	@Override
	public boolean supports(ViewElement htmlElement) {
		return htmlElement.getClass().equals(TextAreaHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement htmlElement) {
		TextAreaHtmlElement textAreaHtmlElement = (TextAreaHtmlElement) htmlElement;
		return "<form:textarea path="+StringUtils.quote(textAreaHtmlElement.getName())+">";				
	}
	
	@Override
	public String processClose(ViewElement htmlElement) {
		TextAreaHtmlElement textAreaHtmlElement = (TextAreaHtmlElement) htmlElement;
		return "</form:textarea>\n"
				+ "<form:errors path='"+textAreaHtmlElement.getName()+"'/>\n";
	}

}

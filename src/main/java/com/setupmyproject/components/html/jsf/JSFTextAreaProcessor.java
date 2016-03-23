package com.setupmyproject.components.html.jsf;

import com.setupmyproject.components.html.TextAreaHtmlElement;
import com.setupmyproject.components.html.ViewElement;

public class JSFTextAreaProcessor extends JSFBasePartProcessor {

	@Override
	public boolean supports(ViewElement viewElement) {
		return viewElement.getClass().equals(TextAreaHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement viewElement) {
		return "<textarea jsf:id='" + inputIdPath(viewElement) + ">#{"
				+ inputValuePath(viewElement) + "}";
	}

	@Override
	public String processClose(ViewElement viewElement) {
		return "</textarea>\n"+"<h:message for='"+inputIdPath(viewElement)+"'/>\n";
	}

}

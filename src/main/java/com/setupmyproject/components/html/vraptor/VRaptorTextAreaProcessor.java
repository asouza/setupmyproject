package com.setupmyproject.components.html.vraptor;

import com.setupmyproject.components.html.TextAreaHtmlElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;

public class VRaptorTextAreaProcessor extends VRaptorBasePartProcessor
		implements ViewPartProcessor {

	@Override
	public boolean supports(ViewElement viewElement) {
		return viewElement.getClass().equals(TextAreaHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement viewElement) {
		String name = inputName(viewElement);
		return "<textarea name='" + name + "' id='" + name + ">${" + name
				+ "}";
	}

	@Override
	public String processClose(ViewElement viewElement) {
		return "</textarea>";
	}

}

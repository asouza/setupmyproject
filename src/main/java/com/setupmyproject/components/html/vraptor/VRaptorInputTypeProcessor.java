package com.setupmyproject.components.html.vraptor;

import com.setupmyproject.components.html.InputTextHtmlElement;
import com.setupmyproject.components.html.ViewElement;

public class VRaptorInputTypeProcessor extends VRaptorBasePartProcessor {

	@Override
	public boolean supports(ViewElement viewElement) {
		return viewElement.getClass().equals(InputTextHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement viewElement) {
		String name = inputName(viewElement);
		InputTextHtmlElement input = (InputTextHtmlElement) viewElement;
		return "<input type='"+input.getInputType()+"' name='" + name + "' id='" + name
				+ "' value='${"+name+"}'/>";
	}
}

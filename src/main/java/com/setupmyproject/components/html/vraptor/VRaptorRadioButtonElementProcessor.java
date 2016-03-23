package com.setupmyproject.components.html.vraptor;

import com.setupmyproject.components.html.RadioButtonHtmlElement;
import com.setupmyproject.components.html.ViewElement;

public class VRaptorRadioButtonElementProcessor extends
		VRaptorBasePartProcessor {

	@Override
	public boolean supports(ViewElement viewElement) {
		return viewElement.getClass().equals(RadioButtonHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement viewElement) {
		String name = inputName(viewElement);
		return "<input type='text' name='" + name + "' id='" + name
				+ "' value='${" + name + "}'/>";
	}

}

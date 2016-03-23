package com.setupmyproject.components.html.basic;

import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;

public class BasicJspOptionElementProcessor implements ViewPartProcessor {

	@Override
	public boolean supports(ViewElement viewElement) {
		return viewElement.getClass().equals(BasicJspOptionElement.class);
	}

	@Override
	public String processOpen(ViewElement viewElement) {
		BasicJspOptionElement option = (BasicJspOptionElement) viewElement;
		String compareExpression = "${"+option.getValuePath()+" == "+option.getValueToBeComparedPath()+" ? 'selected' : ''}";
		return "<option value='${"+option.getValuePath()+"}' "+compareExpression+">${"+option.getLabelPath()+"}</option>\n";
	}

}

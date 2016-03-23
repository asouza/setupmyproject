package com.setupmyproject.components.html.jsf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.setupmyproject.components.html.HtmlPartProcessorGroup;
import com.setupmyproject.components.html.ViewPartProcessor;

public class JSFHtmlFormPartProcessorGroup implements HtmlPartProcessorGroup {

	private List<ViewPartProcessor> partsProcessor = new ArrayList<>();

	{
		partsProcessor.add(new JSFInputTypeProcessor());
		partsProcessor.add(new JSFTextAreaProcessor());
		partsProcessor.add(new JSFSelectOneElementProcessor());
		partsProcessor.add(new JSFRadioButtonProcessor());
		partsProcessor.add(new JSFCheckboxProcessor());
	}

	@Override
	public Collection<ViewPartProcessor> getPartsProcessor() {
		return partsProcessor;
	}

}

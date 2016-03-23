package com.setupmyproject.components.html.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.setupmyproject.components.html.HtmlPartProcessorGroup;
import com.setupmyproject.components.html.ViewPartProcessor;

/**
 * Contem os processadores de tags de formulario do spring
 * 
 * @author alberto
 *
 */
public class SpringHtmlFormPartProcessorGroup implements HtmlPartProcessorGroup {

	private List<ViewPartProcessor> partsProcessor = new ArrayList<>();

	{
		partsProcessor.add(new SpringInputTypeProcessor());
		partsProcessor.add(new SpringTextAreaProcessor());
		partsProcessor.add(new SpringFormElementProcessor());
		partsProcessor.add(new SpringSelectOneElementProcessor());
		partsProcessor.add(new SpringSubmitButtonProcessor());
		partsProcessor.add(new SpringRadioButtonProcessor());
		partsProcessor.add(new SpringCheckboxProcessor());
	}

	@Override
	public Collection<ViewPartProcessor> getPartsProcessor() {
		return partsProcessor;
	}

}

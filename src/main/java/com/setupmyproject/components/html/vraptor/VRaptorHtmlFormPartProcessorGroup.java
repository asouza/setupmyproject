package com.setupmyproject.components.html.vraptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.setupmyproject.components.html.HtmlPartProcessorGroup;
import com.setupmyproject.components.html.JSTLForeachProcessor;
import com.setupmyproject.components.html.ViewPartProcessor;
import com.setupmyproject.components.html.basic.BasicJspOptionElementProcessor;

/**
 * Contem os processadores de tags de formulario do spring
 * 
 * @author alberto
 *
 */
public class VRaptorHtmlFormPartProcessorGroup implements HtmlPartProcessorGroup {

	private List<ViewPartProcessor> partsProcessor = new ArrayList<>();

	{
		partsProcessor.add(new VRaptorInputTypeProcessor());
		partsProcessor.add(new VRaptorTextAreaProcessor());
		partsProcessor.add(new VRaptorSelectOneElementProcessor());
		partsProcessor.add(new VRaptorRadioButtonElementProcessor());
		partsProcessor.add(new VRaptorCheckboxButtonElementProcessor());
		partsProcessor.add(new BasicJspOptionElementProcessor());
		partsProcessor.add(new JSTLForeachProcessor());
		partsProcessor.add(new VRaptorFormHtmlElementProcessor());
	}

	@Override
	public Collection<ViewPartProcessor> getPartsProcessor() {
		return partsProcessor;
	}


}

package com.setupmyproject.components.html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultHtmlPartProcessorGroup implements HtmlPartProcessorGroup {
	
	private List<ViewPartProcessor> partsProcessor = new ArrayList<>();
	
	{
		partsProcessor.add(new HtmlTableProcessor());		
	}

	@Override
	public Collection<ViewPartProcessor> getPartsProcessor() {
		return partsProcessor;
	}

}

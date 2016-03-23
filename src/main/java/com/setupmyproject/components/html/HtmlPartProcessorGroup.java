package com.setupmyproject.components.html;

import java.util.Collection;
import java.util.Optional;

import com.setupmyproject.components.html.spring.SpringHtmlFormPartProcessorGroup;

/**
 * Representa um grupo de processadores de tags do html. Por exemplo, o {@link SpringHtmlFormPartProcessorGroup}
 * @author alberto
 *
 */
public interface HtmlPartProcessorGroup {

	/**
	 * 
	 * @return
	 */
	Collection<ViewPartProcessor> getPartsProcessor();
	
	/**
	 * 
	 * @param viewElement
	 * @return
	 */
	public default Optional<ViewPartProcessor> supports(ViewElement viewElement) {
		return getPartsProcessor().stream()
				.filter((processor) -> processor.supports(viewElement))
				.findFirst();
	}	

}

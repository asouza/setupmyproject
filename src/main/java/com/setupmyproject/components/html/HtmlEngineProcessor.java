package com.setupmyproject.components.html;

import java.util.function.Function;

public interface HtmlEngineProcessor {

	String processDeep(ViewElement viewElement,Function<StringBuilder, Void> innerElementsFunction);

	String processSingle(ViewElement viewElement);
	
	
}

package com.setupmyproject.components.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ViewElement {
	
	private Map<String,Object> metaData = new HashMap<>();
	private String id;

	/**
	 * 
	 * @return mapa com informacoes extra sobre o elemento. Por exemplo, caso esteja usando um framework especifico, pode passar atributos a mais.
	 */
	public Map<String, Object> metaData() {
		return metaData;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public boolean isGroup() {
		return false;
	}
	
	public List<ViewElement> innerElements() {
		return new ArrayList<ViewElement>();
	}
	
	public String generate(HtmlEngineProcessor processor) {
		return generate(processor,this);
	}
	
	public String generate(HtmlEngineProcessor processor,ViewElement currentViewElement) {		
		return processor.processDeep(currentViewElement,(htmlBuilder) -> {			
			for (ViewElement viewElement : currentViewElement.innerElements()) {
				if(viewElement.isGroup()){					
					htmlBuilder.append(generate(processor,viewElement));										
				} else {
					htmlBuilder.append(processor.processSingle(viewElement));					
				}
			}			
			return null;
		});
	}
	
}


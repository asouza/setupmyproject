package com.setupmyproject.components.html;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SelectOneHtmlElement extends GenericInputFormElement {

	private String name;
	private Map<String,Object> metaData = new HashMap<>();

	@SafeVarargs
	public SelectOneHtmlElement(String name, Entry<String, Object>... extraAttributes) {
		this.name = name;
		Arrays.stream(extraAttributes).forEach((e) -> {
			metaData .put(e.getKey(), e.getValue());
		});		
	}

	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Map<String, Object> metaData() {
		return metaData;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((metaData == null) ? 0 : metaData.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectOneHtmlElement other = (SelectOneHtmlElement) obj;
		if (metaData == null) {
			if (other.metaData != null)
				return false;
		} else if (!metaData.equals(other.metaData))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}

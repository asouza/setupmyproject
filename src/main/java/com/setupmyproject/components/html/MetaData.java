package com.setupmyproject.components.html;

import java.util.AbstractMap;

public class MetaData {

	public static AbstractMap.SimpleEntry<String,Object> attribute(String name,Object value){
		return new AbstractMap.SimpleEntry<String,Object>(name,value);		
	}
}

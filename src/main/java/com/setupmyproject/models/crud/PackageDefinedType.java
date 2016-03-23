package com.setupmyproject.models.crud;


public class PackageDefinedType {

	/**
	 * 
	 * @param type
	 * @return verifica se possui "." no tipo... se não possuir, tem chances grandes de ser um modelo.
	 */
	public boolean accepts(String type) {
		return type.contains(".");
	}
}

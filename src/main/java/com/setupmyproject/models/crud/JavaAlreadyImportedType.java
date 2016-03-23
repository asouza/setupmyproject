package com.setupmyproject.models.crud;

public class JavaAlreadyImportedType {

	/**
	 * verifica se já é um tipo importado por padrão.
	 * @param type
	 * @return
	 */
	public boolean accepts(String type) {
		return new JavaLangComplextType().accepts(type)
				|| new PrimitiveTypes().accepts(type);
	}
}

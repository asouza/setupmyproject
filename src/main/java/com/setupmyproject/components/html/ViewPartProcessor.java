package com.setupmyproject.components.html;

import com.setupmyproject.models.crud.CrudField;
import com.setupmyproject.models.crud.CrudModel;

public interface ViewPartProcessor {

	boolean supports(ViewElement viewElement);

	String processOpen(ViewElement viewElement);
	
	default String processClose(ViewElement viewElement) {
		return "";
	}

	/**
	 * Talvez algum {@link ViewPartProcessor} necessite adicionar informações nos metaDados do {@link ViewElement}. Use quando
	 * precisar.
	 * @param input
	 * @param f
	 * @param crudModel
	 */
	default void prepareInputMetaData(ViewElement input, CrudField f,
			CrudModel crudModel){
		
	}

}

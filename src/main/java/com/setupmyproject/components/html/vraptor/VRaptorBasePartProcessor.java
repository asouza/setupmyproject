package com.setupmyproject.components.html.vraptor;

import com.setupmyproject.components.html.GenericInputFormElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;
import com.setupmyproject.models.crud.CrudField;
import com.setupmyproject.models.crud.CrudModel;

public abstract class VRaptorBasePartProcessor implements ViewPartProcessor{

	public static final String PARAM_CONTROLLER_NAME = "paramControllerName";

	@Override
	public void prepareInputMetaData(ViewElement input, CrudField f,
			CrudModel crudModel) {
		input.metaData().put(PARAM_CONTROLLER_NAME, crudModel.getClassNameAsProperty());
	}
	
	public String inputName(ViewElement inputElement){
		String name = inputElement.metaData().get(
				VRaptorBasePartProcessor.PARAM_CONTROLLER_NAME)
				+ "." + ((GenericInputFormElement)inputElement).getName();
		return name;
	}
}

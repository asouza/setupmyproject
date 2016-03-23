package com.setupmyproject.components.html.jsf;

import com.setupmyproject.components.html.GenericInputFormElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;
import com.setupmyproject.models.crud.CrudField;
import com.setupmyproject.models.crud.CrudModel;

public abstract class JSFBasePartProcessor implements ViewPartProcessor {

	public static final String INPUT_VALUE_PATH = "inputValueBasePath";
	private static final String INPUT_ID_PATH = "inputId";

	@Override
	public void prepareInputMetaData(ViewElement viewElement, CrudField f,
			CrudModel crudModel) {
		GenericInputFormElement inputElement = (GenericInputFormElement) viewElement;
		viewElement.metaData().put(
				INPUT_VALUE_PATH,
				crudModel.getControllerNameAsProperty() + "."
						+ crudModel.getClassNameAsProperty() + "."
						+ inputElement.getName());
		String idPath = crudModel.getClassNameAsProperty() + "-"+inputElement.getName().replace(".", "-");
		viewElement.metaData().put(INPUT_ID_PATH,idPath);
		viewElement.setId(idPath);
	}

	public String inputValuePath(ViewElement inputElement) {
		return inputElement.metaData()
				.get(JSFBasePartProcessor.INPUT_VALUE_PATH).toString();
	}

	public String inputIdPath(ViewElement inputElement) {
		return inputElement.metaData().get(JSFBasePartProcessor.INPUT_ID_PATH)
				.toString();
	}
}

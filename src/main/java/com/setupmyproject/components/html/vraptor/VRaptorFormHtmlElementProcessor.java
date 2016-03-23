package com.setupmyproject.components.html.vraptor;

import com.setupmyproject.components.html.FormHtmlElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;
import com.setupmyproject.models.crud.CrudField;
import com.setupmyproject.models.crud.CrudModel;

/**
 * Apenas para trocar a action do form
 * @author alberto
 *
 */
public class VRaptorFormHtmlElementProcessor implements ViewPartProcessor {

	@Override
	public boolean supports(ViewElement viewElement) {
		return viewElement.getClass().equals(FormHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement viewElement) {
		return "";
	}
	
	@Override
	public void prepareInputMetaData(ViewElement viewElement, CrudField f,
			CrudModel crudModel) {
	}

}

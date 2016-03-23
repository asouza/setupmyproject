package com.setupmyproject.components.html.vraptor;

import java.util.Optional;

import com.setupmyproject.components.html.DefaultHtmlEngineProcessor;
import com.setupmyproject.components.html.HtmlEngineProcessor;
import com.setupmyproject.components.html.SelectOneHtmlElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.basic.BasicJspOptionElement;
import com.setupmyproject.components.html.jstl.JSTLForeachElement;
import com.setupmyproject.models.crud.CrudField;
import com.setupmyproject.models.crud.CrudModel;

public class VRaptorSelectOneElementProcessor extends VRaptorBasePartProcessor {

	public static final String ITEMS_EXPRESSION = "items";
	public static final String ITEMS_LABEL = "itemLabel";

	@Override
	public boolean supports(ViewElement viewElement) {
		return viewElement.getClass().equals(SelectOneHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement viewElement) {
		String name = inputName(viewElement);
		StringBuilder select = new StringBuilder("<select name='" + name
				+ "' id='" + name + "'>\n");
		JSTLForeachElement foreachElement = new JSTLForeachElement(viewElement
				.metaData().get(ITEMS_EXPRESSION).toString(), "currentItem");
		foreachElement.insertElement(new BasicJspOptionElement(
				"currentItem.id", "currentItem."
						+ viewElement.metaData().get(ITEMS_LABEL), name));
		
		HtmlEngineProcessor processor = new DefaultHtmlEngineProcessor(new VRaptorHtmlFormPartProcessorGroup());
		select.append(foreachElement.generate(processor));
		
		return select.toString();
	}

	@Override
	public String processClose(ViewElement viewElement) {
		return "</select>";
	}

	@Override
	public void prepareInputMetaData(ViewElement input, CrudField f,
			CrudModel crudModel) {
		super.prepareInputMetaData(input, f, crudModel);
		Optional<CrudField> possibleField = crudModel.getRelatedCrudField(f);
		if (possibleField.isPresent()) {
			CrudField foundField = possibleField.get();
			input.metaData().put(ITEMS_EXPRESSION,
					"${" + f.getNamePluralized() + "}");
			input.metaData().put(ITEMS_LABEL, foundField.getSelectLabelField());
		}
	}

}

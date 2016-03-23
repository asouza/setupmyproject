package com.setupmyproject.components.html.jsf;

import java.util.Map;
import java.util.Optional;

import org.springframework.util.Assert;

import com.setupmyproject.components.html.SelectOneHtmlElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.models.crud.CrudField;
import com.setupmyproject.models.crud.CrudModel;

public class JSFSelectOneElementProcessor extends JSFBasePartProcessor {

	public static final String ITEMS_EXPRESSION = "items";
	public static final String ITEMS_LABEL = "itemLabel";

	@Override
	public boolean supports(ViewElement viewElement) {
		return viewElement.getClass().equals(SelectOneHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement htmlElement) {
		Map<String, Object> metaData = htmlElement.metaData();
		Assert.isTrue(
				metaData.containsKey(ITEMS_EXPRESSION),
				"Você deve passar a chave <b>items</b> com o caminho para ser usado  na expressão que deve ser adicionada na view. Ex: products");

		Assert.isTrue(
				metaData.containsKey(ITEMS_LABEL),
				"Você deve passar a chave <b>itemLabel</b> com a propriedade que deve ser usada para label do select. Ex: Caso vc tenha uma classe Product com a propriedade name, passe name.");

		StringBuilder select = new StringBuilder("<h:selectOneMenu value='#{"
				+ inputValuePath(htmlElement) + "}' id='"
				+ inputIdPath(htmlElement) + "'>\n");
		select.append("<f:selectItems value='#{"+metaData.get(ITEMS_EXPRESSION)+"}' var='currentItem' itemLabel='#{currentItem."+metaData.get(ITEMS_LABEL)+"}' itemValue='#{currentItem.id}' />\n");
		return select.toString();
	}
	
	@Override
	public String processClose(ViewElement htmlElement) {
		StringBuilder close = new StringBuilder("</h:selectOneMenu>\n");
		close.append("<h:message for='"+inputIdPath(htmlElement)+"'/>\n");
		return close.toString();
	}
	
	@Override
	public void prepareInputMetaData(ViewElement input, CrudField f,
			CrudModel crudModel) {
		super.prepareInputMetaData(input, f, crudModel);
		Optional<CrudField> possibleField = crudModel.getRelatedCrudField(f);
		if(possibleField.isPresent()){
			CrudField foundField = possibleField.get();
			input.metaData().put(ITEMS_EXPRESSION,crudModel.getControllerNameAsProperty()+"."+f.getNamePluralized());
			input.metaData().put(ITEMS_LABEL,foundField.getSelectLabelField());
		}
	}	
}

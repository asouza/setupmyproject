package com.setupmyproject.components.html.spring;

import java.util.Map;
import java.util.Optional;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.setupmyproject.components.html.SelectOneHtmlElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;
import com.setupmyproject.models.crud.CrudField;
import com.setupmyproject.models.crud.CrudModel;

public class SpringSelectOneElementProcessor implements ViewPartProcessor {

	public static final String ITEMS_EXPRESSION = "items";
	public static final String ITEMS_LABEL = "itemLabel";

	@Override
	public boolean supports(ViewElement htmlElement) {
		return htmlElement.getClass().equals(SelectOneHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement htmlElement) {
		Map<String, Object> metaData = htmlElement.metaData();
		SelectOneHtmlElement selectOneHtmlElement = (SelectOneHtmlElement) htmlElement;
		Assert.isTrue(
				metaData.containsKey(ITEMS_EXPRESSION),
				"Você deve passar a chave <b>items</b> com a expressão que deve ser adicionada na view. Ex: ${collection}");
		
		Assert.isTrue(
				metaData.containsKey(ITEMS_LABEL),
				"Você deve passar a chave <b>itemLabel</b> com a propriedade que deve ser usada para label do select. Ex: Caso vc tenha uma classe Product com a propriedade name, passe name.");
		
		
		return "<form:select path='" + selectOneHtmlElement.getName() + "' items='"
				+ metaData.get(ITEMS_EXPRESSION)
				+ "' itemValue='id' itemLabel="+StringUtils.quoteIfString(metaData.get(ITEMS_LABEL))+">\n";
	}

	@Override
	public String processClose(ViewElement htmlElement) {
		SelectOneHtmlElement selectOneHtmlElement = (SelectOneHtmlElement) htmlElement;
		return "</form:select>\n" + "<form:errors path='"
				+ selectOneHtmlElement.getName() + "'/>\n";
	}

	@Override
	public void prepareInputMetaData(ViewElement input, CrudField f,
			CrudModel crudModel) {
		Optional<CrudField> possibleField = crudModel.getRelatedCrudField(f);
		if(possibleField.isPresent()){
			CrudField foundField = possibleField.get();
			//TODO era melhor deixar o método que adiciona direto no ViewElement? 
			input.metaData().put(ITEMS_EXPRESSION,"${"+f.getNamePluralized()+"}");
			input.metaData().put(ITEMS_LABEL,foundField.getSelectLabelField());
		}
	}

}

package com.setupmyproject.components.html;

import java.util.Arrays;
import java.util.List;

import com.setupmyproject.components.html.spring.SpringFormElementProcessor;
import com.setupmyproject.components.html.spring.SpringHtmlFormPartProcessorGroup;
import com.setupmyproject.components.html.spring.SpringSelectOneElementProcessor;

public class FormExperiment {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		FormHtmlElement form = new FormHtmlElement("/bla",
				FormRequestMethod.POST, Arrays.asList(MetaData.attribute(
						SpringFormElementProcessor.COMMAND_NAME_ATTRIBUTE,
						"product")));

		form.inputText("name");
		form.textArea("description");
		form.selectOne("category", MetaData.attribute(
				SpringSelectOneElementProcessor.ITEMS_EXPRESSION,
				"${categories}"), MetaData.attribute(
				SpringSelectOneElementProcessor.ITEMS_LABEL, "name"));
		form.radionButton("radiobutton");

		HtmlEngineProcessor processor = new DefaultHtmlEngineProcessor(
				new SpringHtmlFormPartProcessorGroup());

		List<ViewElement> elements = form.innerElements();
		for (ViewElement viewElement : elements) {
			System.out.println(viewElement.generate(processor));
		}
	}
}

package com.setupmyproject.components.html;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

public class DefaultHtmlEngineProcessor implements HtmlEngineProcessor {

	private HtmlPartProcessorGroup group;

	public DefaultHtmlEngineProcessor(HtmlPartProcessorGroup group) {
		Assert.notNull(group, "O grupo de processadores não pode ser vazio");
		this.group = group;
	}

	@Override
	public String processDeep(ViewElement viewElement,
			Function<StringBuilder, Void> f) {
		ViewPartProcessor choosedHtmlProcessor = findHtmlPartProcessor(viewElement);
		StringBuilder openTag = new StringBuilder(
				choosedHtmlProcessor.processOpen(viewElement));
		f.apply(openTag);

		return openTag.append(choosedHtmlProcessor.processClose(viewElement))
				.toString();
	}

	private ViewPartProcessor findHtmlPartProcessor(ViewElement viewElement) {
		List<ViewPartProcessor> filteredPartsProcessor = group
				.getPartsProcessor().stream()
				.filter((partProcessor) -> partProcessor.supports(viewElement))
				.collect(Collectors.toList());

		Assert.notEmpty(filteredPartsProcessor,
				"Não foi encontrado nenhum processador de html para "
						+ viewElement.getClass());

		Assert.state(
				filteredPartsProcessor.size() == 1,
				"Tem mais de um processador de html para "
						+ viewElement.getClass());
		
		ViewPartProcessor choosedHtmlProcessor = filteredPartsProcessor.get(0);
		return choosedHtmlProcessor;
	}

	@Override
	public String processSingle(ViewElement viewElement) {
		ViewPartProcessor choosedHtmlProcessor = findHtmlPartProcessor(viewElement);
		return choosedHtmlProcessor.processOpen(viewElement)
				+ choosedHtmlProcessor.processClose(viewElement);
	}

}

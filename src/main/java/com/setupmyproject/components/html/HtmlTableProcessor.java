package com.setupmyproject.components.html;

import com.setupmyproject.components.html.jstl.JSTLForeachElement;

public class HtmlTableProcessor implements ViewPartProcessor {

	@Override
	public boolean supports(ViewElement viewElement) {
		return viewElement.getClass().equals(DynamicTableHtmlElement.class);
	}

	@Override
	public String processOpen(ViewElement viewElement) {
		DynamicTableHtmlElement dynamicTableHtmlElement = (DynamicTableHtmlElement) viewElement;
		StringBuilder builder = new StringBuilder("<table>\n");
		builder.append("<tr>\n");
		dynamicTableHtmlElement.getHeader().getTds().forEach((td) -> {
			builder.append("<td>").append(td.getValue()).append("</td>\n");
		});
		builder.append("</tr>\n");

		JSTLForeachElement forEach = new JSTLForeachElement("${"
				+ dynamicTableHtmlElement.getCollectionVariable() + "}",
				dynamicTableHtmlElement.getVarName());
		
		JSTLForeachProcessor jstlForeachProcessor = addForEach(builder, forEach);
		
		
		addRows(dynamicTableHtmlElement, builder);
				
		
		builder.append(jstlForeachProcessor.processClose(forEach));

		return builder.toString();
	}

	private void addRows(DynamicTableHtmlElement dynamicTableHtmlElement,
			StringBuilder builder) {
		HtmlTableRowExpressionLanguageProcessor rowProcessor = new HtmlTableRowExpressionLanguageProcessor();
		RowExpression rowExpression = dynamicTableHtmlElement.getRowExpression();
		builder.append(rowProcessor.processOpen(rowExpression));
		builder.append(rowProcessor.processClose(rowExpression));
	}

	private JSTLForeachProcessor addForEach(StringBuilder builder,
			JSTLForeachElement forEach) {
		JSTLForeachProcessor jstlForeachProcessor = new JSTLForeachProcessor();
		builder.append(jstlForeachProcessor.processOpen(forEach));
		return jstlForeachProcessor;
	}

	@Override
	public String processClose(ViewElement viewElement) {
		return "</table>\n";
	}

}

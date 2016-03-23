package com.setupmyproject.components.html;

public class HtmlTableRowExpressionLanguageProcessor implements ViewPartProcessor {

	@Override
	public boolean supports(ViewElement viewElement) {
		return viewElement.getClass().equals(RowExpression.class);
	}

	@Override
	public String processOpen(ViewElement viewElement) {
		RowExpression rowExpression = (RowExpression) viewElement;
		StringBuilder builder = new StringBuilder();
		builder.append("<tr>\n");
		rowExpression.getValueExpressions().forEach((exp) -> {
			builder.append("<td>${"+exp+"}</td>\n");
		});
		return builder.append("</tr>\n").toString();
	}

}

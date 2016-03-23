package com.setupmyproject.components.html;


public class TableExperiment {

	public static void main(String[] args) {
		DynamicTableHtmlElement table = new DynamicTableHtmlElement("products","object");
		table.header().td("name")
			.td("id")
			.td("bla");	
		table.rowExpression().td("object.name").td("object.id").td("object.bla");
		
		HtmlEngineProcessor processor = new DefaultHtmlEngineProcessor(new DefaultHtmlPartProcessorGroup());
		
		System.out.println(table.generate(processor));		
		
	}
}

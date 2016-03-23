package com.setupmyproject.components.html;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

public class DynamicTableHtmlElement extends ViewElement{
	
	private List<ViewElement> innerElements = new ArrayList<ViewElement>();
	private TableHeaderHtmlElement header;
	private String collectionVariable;
	private String varName;
	private RowExpression rowExpression;

	public DynamicTableHtmlElement(String collectionVariable, String varName) {
		this.collectionVariable = collectionVariable;
		this.varName = varName;
	}
	
	public String getCollectionVariable() {
		return collectionVariable;
	}
	
	public String getVarName() {
		return varName;
	}
	
	public TableHeaderHtmlElement getHeader() {
		return header;
	}
	
	public RowExpression getRowExpression() {
		return rowExpression;
	}

	public TableHeaderHtmlElement header() {
		this.header = new TableHeaderHtmlElement();		
		return this.header;
	}

	public DynamicTableHtmlElement insertElement(ViewElement viewElement) {
		this.innerElements.add(viewElement);
		return this;		
	}

	@Override
	public List<ViewElement> innerElements() {
		return innerElements;
	}
	
	@Override
	public String generate(HtmlEngineProcessor processor) {
		Assert.state(header!=null, "É necessário configurar o header antes de gerar a tabela");
		Assert.state(rowExpression!=null, "É necessário configurar as expressoes das linhas antes de gerar a tabela");
		return super.generate(processor);
	}
	
	@Override
	public String generate(HtmlEngineProcessor processor,
			ViewElement currentViewElement) {
		Assert.state(header!=null, "É necessário configurar o header antes de gerar a tabela");
		Assert.state(rowExpression!=null, "É necessário configurar as expressoes das linhas antes de gerar a tabela");
		return super.generate(processor, currentViewElement);
	}

	public RowExpression rowExpression() {
		this.rowExpression = new RowExpression();
		return rowExpression;
	}

}

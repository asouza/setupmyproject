package com.setupmyproject.components.html;

import java.util.ArrayList;
import java.util.List;

public class TableHeaderHtmlElement extends ViewElement{
	
	private List<TableDataHtmlElement> tds = new ArrayList<>();

	/**
	 * 
	 * @param value valor que deve ser coluna no header.
	 * @return
	 */
	public TableHeaderHtmlElement td(String value) {
		tds.add(new TableDataHtmlElement(value));
		return this;
	}
	
	public List<TableDataHtmlElement> getTds() {
		return tds;
	}

}

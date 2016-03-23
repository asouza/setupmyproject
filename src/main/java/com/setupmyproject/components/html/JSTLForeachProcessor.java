package com.setupmyproject.components.html;

import com.setupmyproject.components.html.jstl.JSTLForeachElement;

public class JSTLForeachProcessor implements ViewPartProcessor {

	@Override
	public boolean supports(ViewElement viewElement) {
		return viewElement.getClass().equals(JSTLForeachElement.class);
	}

	@Override
	public String processOpen(ViewElement viewElement) {
		JSTLForeachElement jstlForeachElement = (JSTLForeachElement) viewElement;		
		return "<c:forEach items='"+jstlForeachElement.getItemsExpression()+"' var='"+jstlForeachElement.getVarName()+"'>\n";
	}
	
	@Override
	public String processClose(ViewElement viewElement) {
		return "</c:forEach>\n";
	}

}

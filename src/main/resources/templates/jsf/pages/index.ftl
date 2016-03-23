<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://xmlns.jcp.org/jsf/core"
	xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
	xmlns:h="http://xmlns.jcp.org/jsf/html">
<h:head>
	<title>Hello World!</title>
	<#include "../../donate-css.ftl">	
</h:head>
<h:body>
	<h:form>
		<h:panelGrid columns="2">
			<h:outputLabel for="name" value="Your Name" />
			<h:inputText value="${"#"}{helloWorldController.name}" />
			
			<h:commandButton value="Click me!" action="${"#"}{helloWorldController.action}" />
			<h:panelGroup/>
			
			<h:outputText value="${"#"}{helloWorldController.label}" />
		</h:panelGrid>
	</h:form>
	
	<#include "../../donate.ftl">	
</h:body>
</html>
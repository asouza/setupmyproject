<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/import_tags.jsp"%>
<spring:message code="home.title" text="CRUD example?" var="title" />
<template:page title="${title}">
	<jsp:attribute name="extraScripts">		
		<link rel="stylesheet" href="<c:url value="/js/codemirror/lib/codemirror.css"/>">		
		<link rel="stylesheet" href="<c:url value="/js/codemirror/theme/zenburn.css"/>">		
		<link rel="stylesheet" href="<c:url value="/js/codemirror/addon/hint/show-hint.css"/>">
		<script src="<c:url value="/js/codemirror/lib/codemirror.js"/>"></script>
		<script src="<c:url value="/js/codemirror/addon/hint/show-hint.js"/>"></script>
		<script src="<c:url value="/js/codemirror/addon/hint/xml-hint.js"/>"></script>
		<script src="<c:url value="/js/codemirror/addon/display/placeholder.js"/>"></script>
		<script src="<c:url value="/js/codemirror/mode/xml/xml.js"/>"></script>
		<script src="<c:url value="/js/schema/schema-crud.js"/>"></script>
		<script>
			$(function(){
				$("[name=crudType]").click(function(event){
					if($(event.target).val() === 'CRUD_CUSTOM'){
						$("#customBox").show();
						return;
					};
					$("#customBox").hide();
				});
			});
		</script>
		<script>
			var editor = CodeMirror.fromTextArea(document.getElementById("customModelXml"), {
		        mode: "xml",
		        theme: "zenburn",		        
		        extraKeys: {
		            "Ctrl-Space": "autocomplete"
		        },	
		        hintOptions: {schemaInfo: schemaCrudTags}
		      });		
		</script>
	</jsp:attribute>
	<jsp:body>
		<h3>CRUD example</h3>
		<form:form commandName="crudForm" servletRelativeAction="/setup/crud/addons">
			<p style="color:rgb(228, 67, 67);font-size: 15px;margin-bottom: 1em">
				Did you chose a JPA plugin for your framework? It is needed to provide EntityManager for the crud 
				classes that will be generated :).
			</p>		
			<div>
				<customForm:radiobuttons items="${crudTypes}" path="crudType" itemLabel="label" itemTitleMessageKey="getTooltipKey"/>
			</div>
			<div id="customBox" style="${!crudForm.isCustomCrudSelected() ? 'display:none' : ''}">
				<a href="https://github.com/asouza/setupmyproject-xml-crud-example/blob/master/README.md" target="_blank">Click here to learn how to customize your crud</a>
				<form:textarea path="customModelXml" rows="10" cols="63" placeholder="Write your custom xml. You can use Ctrl+space for autocomplete :)"/>
				<form:errors path="crudType" cssClass="validation-error"/>
			</div>
			<input type="submit" class="next-wizard"
				value="<spring:message code="submit.next_step" text="database setup"/>" />
		</form:form>
	</jsp:body>
</template:page>
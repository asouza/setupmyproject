<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/import_tags.jsp"%>
<spring:message code="home.title" text="Choose your java version" var="title" />
<template:page title="${title}">
<h3>Versions</h3>
	<form:form commandName="javaConfigurationForm" servletRelativeAction="/setup/java/addons">
		<div>
			
			<customForm:radiobuttons items="${versions}" path="version"  itemLabel="label" itemTitleMessageKey="getTooltipKey"/>
		</div>
		<input type="submit" class="next-wizard"
			value="<spring:message code="submit.next_step" text="choose your java configruation"/>" />
	</form:form>
</template:page>
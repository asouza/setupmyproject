<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/import_tags.jsp"%>
<spring:message code="home.title" text="Choose your addons" var="title" />
<template:page title="${title}">
<h3>Addons</h3>
	<form:form commandName="spaOptionForm" servletRelativeAction="/setup/spa/options">
		<div>
			<customForm:radiobuttons items="${options}" path="option" itemLabel="label" itemTitleMessageKey="getTooltipKey"/>
		</div>		
		<input type="submit" class="next-wizard"
			value="<spring:message code="submit.next_step" text="type of project"/>" />
	</form:form>
</template:page>
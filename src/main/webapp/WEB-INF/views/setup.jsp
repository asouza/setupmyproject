<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/import_tags.jsp"%>
<spring:message code="home.title" text="Start your setup" var="title" />
<template:page title="${title}">
	<h3>Mvc</h3>
	<form:form commandName="configurationForm" servletRelativeAction="/setup/projectType">
		<div>
			<customForm:radiobuttons path="projectType" items="${projectTypes}" itemLabel="label" itemTitleMessageKey="getTooltipKey"/>
			<form:errors path="projectType"/>
		</div>
		<input class="next-wizard" type="submit" value="<spring:message code="submit.next_step" text="type of project"/>" />
	</form:form>
</template:page>
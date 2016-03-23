<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/import_tags.jsp"%>
<spring:message code="home.title" text="Type of your current project" var="title" />
<template:page title="${title}">
	<h3>Type of your current project</h3>
	<form:form commandName="existingConfigurationForm" servletRelativeAction="/existing/setup">
		<div>
			<customForm:radiobuttons path="projectType" items="${projectTypes}" itemLabel="label" itemTitleMessageKey="getTooltipKey"/>
			<form:errors path="projectType"/>
		</div>
		<input class="next-wizard" type="submit" value="<spring:message code="submit.next_step" text="type of project"/>" />
	</form:form>
</template:page>
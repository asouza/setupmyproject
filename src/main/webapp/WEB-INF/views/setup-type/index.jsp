<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/import_tags.jsp"%>
<spring:message code="home.title" text="Start your setup" var="title" />
<template:page title="${title}">
	<h3>Type of your project</h3>
	<form:form commandName="setupTypeForm" servletRelativeAction="/setup/type">
		<div>
			<customForm:radiobuttons path="setupType" items="${setupTypes}" itemLabel="label" itemTitleMessageKey="getTooltipKey"/>
			<form:errors path="setupType"/>
		</div>
		<input class="next-wizard" type="submit" value="<spring:message code="submit.next_step" text="type of project"/>" />
	</form:form>
</template:page>
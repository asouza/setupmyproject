<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/import_tags.jsp"%>
<spring:message code="home.title" text="Choose your db" var="title" />
<template:page title="${title}">
	<h3>Db Type</h3>
	<form:form commandName="dbAddonForm" servletRelativeAction="/setup/db/addons">
		<div>
			<customForm:radiobuttons items="${dbTypes}" path="dbType" itemLabel="label" itemTitleMessageKey="getTooltipKey"/>
			
		</div>
		<input type="submit" class="next-wizard"
			value="<spring:message code="submit.next_step" text="database setup"/>" />
	</form:form>
</template:page>
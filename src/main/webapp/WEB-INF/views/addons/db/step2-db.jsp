<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/import_tags.jsp"%>
<spring:message code="home.title" text="Choose your db" var="title" />
<template:page title="${title}">
	<h3>Database access information</h3>
	<form:form commandName="dbAccessInfoForm" servletRelativeAction="/setup/db/info">
		<div>
			<label id="databaseName">Database name(optional)</label>
			<form:input path="databaseName" placeHolder="databaseName"/>
			<form:errors path="databaseName"/>
			<label id="login">Database login(optional)</label>
			<form:input path="login" placeHolder="login"/>
			<form:errors path="login"/>
			<label id="password">Database password(optional)</label>
			<form:input path="password" placeHolder="password"/>
			<form:errors path="password"/>
		</div>
		<input type="submit" class="next-wizard"
			value="<spring:message code="submit.next_step" text="database access info"/>" />
	</form:form>
</template:page>
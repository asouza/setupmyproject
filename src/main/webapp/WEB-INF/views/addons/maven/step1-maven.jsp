<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/import_tags.jsp"%>
<spring:message code="maven.title" text="Setup maven" var="title" />
<template:page title="${title}">
		<h3>Maven info</h3>
		<form:form commandName="mavenSetupForm" servletRelativeAction="/setup/maven">
			<div>
				<label id="icon"><i class="fa fa-briefcase"></i></label>
				<form:input path="basePackage" placeHolder="br.com.yourcompany.projectName"/>
				<form:errors path="basePackage"/>				
			</div>
			<input type="submit" class="next-wizard"
				value="<spring:message code="submit.next_step" text="maven information"/>" />
		</form:form>
</template:page>
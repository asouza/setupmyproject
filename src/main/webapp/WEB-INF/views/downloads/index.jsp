<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/import_tags.jsp"%>
<spring:message code="home.title" text="Download your project" var="title" />
<template:page title="${title}">
<%-- 	<h3>${title}</h3> --%>
	<a class="download-button" href="${spring:mvcUrl('DC#download').arg(0,token).build()}">
		<i class="fa fa-cloud-download"></i>
		<spring:message code="downloads.link" text="download now!"/>
	</a>
	<p class="text-wait">
		<i class="fa fa-refresh fa-2"></i>
		<br>Please wait while your project is being generated.<br>Your download will start automatically quickly...<br/>
		If any problem happens, you are free to come back to this URL to try your download again. 
	    </p>
	<script>
		setTimeout(function(){
			location.href = "${spring:mvcUrl('DC#download').arg(0,token).build()}";
		},2000);
	</script>	
</template:page>
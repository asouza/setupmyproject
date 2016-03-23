<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/import_tags.jsp"%>
<template:page title="">
	<h3>Oops, we had a problem.</h3>
	<p>It is just a guess, but you tried to access a page in the middle of the wizard direct from your browser? I mean, without click the form button?</p>
	<p>If this is the case, try to start over and follow the steps through the wizard :). <a class="simple-link" href="${spring:mvcUrl('startWizard').build()}">Just clik here!</a></p>
</template:page>
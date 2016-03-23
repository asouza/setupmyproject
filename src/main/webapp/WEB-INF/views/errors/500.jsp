<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/import_tags.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html class="no-js" lang="pt">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600' rel='stylesheet'
	type='text/css'>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<title>${title}</title>
</head>
<body>

	<div class="wizard-comtainer">

		<div class="wizard">
			<div class="formBox">
				<header>
					<h1>
						<a href="${pageContext.request.contextPath}/"> <img src="${pageContext.request.contextPath}/images/myproject.svg"
							alt="SetupMyProject">
						</a>
					</h1>
					<hr>
					<!-- <ul class="wizardPass">
	                <li class="passChecked"><i class="fa fa-check"></i> mvc</li>
	                <li class="passActive"><i class="fa fa-hand-o-down"></i> project</li>
	                <li class="passNotChecked"><i class="fa fa-times"></i> template</li>
	                <li class="passNotChecked"><i class="fa fa-times"></i> securit</li>
	                <li class="passNotChecked"><i class="fa fa-times"></i> server</li>
	            </ul> -->
				</header>
				<h3>Oops, we had a problem.</h3>
				<p>Don't worry, our team was notified about the issue</p>				
			</div>
			<div class="resume">
				<header>
					<h2>
						<i class="fa fa-shopping-cart"></i> Time expected to configure
					</h2>
				</header>
				<ul>
				</ul>
			</div>
		</div>

	</div>

	<div class="menu-footer">
		<ul>
			<li><a href="<c:url value="/#setupContactForm"/>">Contact</a></li>
		</ul>
	</div>

	<div id="copyright">&copy; Setup My Project</div>

	<!--     <footer>
        <a class="backButton" href="#"><i class="fa fa-arrow-circle-left"></i> back</a>
        <a class="nextButton" href="#">next <i class="fa fa-arrow-circle-right"></i></a>
    </footer> -->

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/tooltip.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<c:if test="${env.isProd()}">
		<script src="${pageContext.request.contextPath}/js/analytics.js"></script>
	</c:if>

</body>
</html>


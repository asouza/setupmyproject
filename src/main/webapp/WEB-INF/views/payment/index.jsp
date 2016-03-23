<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/import_tags.jsp"%>
<spring:message code="payment.title" text="Enjoy your new project"
	var="title" />
<template:page title="${title}">
	<h3 class="titledownload">${title}</h3>
	<c:if test="${projectType.isPayed() && !env.isDev()}">
		<div id="brl-button">
			<img src="${pageContext.request.contextPath}/images/flags/ptbr.png" alt="brazilian flag"/> 
			<p class="textPay">
				A nossa ideia é que você inicie o seu projeto o mais rápido possível, sem perder tempo configurando frameworks e outros detalhes. Ainda tem muito mais por vir, projetos já com templates configurados, mais opções de plugins dentro dos frameworks etc. Entendemos que nosso projeto realmente faz você economizar tempo, então, se for possível, gostaríamos que você nos desse uma ajuda fazendo uma doação.  									
			</p>
			<p class="textPay">
				A nossa conta do Paypal é brasileira e, se a sua conta no Paypal também for brasileira, por conta das leis do nosso país, temos que pedir que você doe em reais. Ao lado tem a nossa sugestão :).
			</p>		
			<script
					src="<c:url value="/js/paypal-button.min.js?merchant=${env.getProperty('paypal.merchant_id')}"/>"
					data-button="donate" data-name="Configured project"
					data-amount-editable="5.0"
					data-env=${env.getProperty('paypal.env') == 'live'?'""':'"sandbox"'}
					data-callback="${spring:mvcUrl('PC#confirm').arg(0,token).build()}"
					data-currency="BRL" 
					data-locale="pt_BR" 
					async
					data-return="${spring:mvcUrl('DC#index').arg(0,token).build()}">
			</script>
		</div>
		<div id="usd-button">
			<img src="${pageContext.request.contextPath}/images/flags/usen.png" alt="non brazilian flag"/>
			<p class="textPay">
				Our main goal is that you can start your project as fast as possible, without wasting time setting up frameworks and other details. There are a lot of things to come like project with already configured templates, more options of plugins for each framework etc. We really think that our project saves your time, so, if you can, we would like that you to contribute with us making a donation. 
			</p>
			<p class="textPay">
				If you want to donate in dollar, use this button :).				
			</p>
			<script
					src="<c:url value="/js/paypal-button.min.js?merchant=${env.getProperty('paypal.merchant_id')}"/>"
					data-button="donate" data-name="Configured project"
					data-amount-editable="5.0"
					data-env=${env.getProperty('paypal.env') == 'live'?'""':'"sandbox"'}
					data-callback="${spring:mvcUrl('PC#confirm').arg(0,token).build()}"
					data-currency="USD" 
					data-return="${spring:mvcUrl('DC#index').arg(0,token).build()}"
					async
					>
			</script>
		</div>			
	</c:if> 
		
	<c:if test="${not projectType.isANewProject()}">
		<div class="alert alert-warning" role="alert">
			Please, import this project as <b>archive file</b> in your eclipse. Also skip the pom.xml and the readme, because they are already created in your project.
		</div>
	</c:if>
	<form action="${spring:mvcUrl('PC#freemium').arg(0,token).build()}"
				method="post" class="formPayment">
		<c:if test="${not buyerInfoTaken.isDone()}">
				<label for="email" class="formPayment-label">Can I have your email?</label>
				<input type="email" class="formPayment-input" id="email" name="user.email" placeholder="you@example.com" autofocus/>
		</c:if>				
		<input type="submit" class="next-wizard formPayment-downloadButton"
				value="<spring:message code="submit.freemium" text="Download"/>" />
	</form>
</template:page>

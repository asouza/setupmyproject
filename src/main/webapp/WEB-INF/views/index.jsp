<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/import_tags.jsp"%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>Setup My Project</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />
		<meta name="google-site-verification" content="EqctaPNT7KCjLSFyaExZQ_93Yk24ZgjI-OspOgnT0Mo" />
	</head>
	<body class="homepage">
		<div id="page-wrapper">

				<div id="header-wrapper" class="wrapper">

							<div id="logo">
								<h1>
									<img src="${pageContext.request.contextPath}/images/logo2.svg" alt="SetupMyProject">
								</h1>
								<p>Setup your project in less than five minutes</p>
							</div>

					<div id="header">

					</div>
				</div>

				<div id="intro-wrapper" class="wrapper style1">
					<div class="title">How it works</div>
					<section id="intro" class="container">
						<p class="style1">Choose, download, import and run your application as fast as possible.</p>
						<p class="style2">						
							In a few minutes start your already configured project, with well known technologies in the market.
						</p>

						<ul class="actions">
							<li><a href="${spring:mvcUrl('setupTypeAction').build()}" class="button style3 big">Start now</a></li>
							<li>
								<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
								<input type="hidden" name="cmd" value="_donations">
								<input type="hidden" name="business" value="alots.ssa@gmail.com">
								<input type="hidden" name="lc" value="US">
								<input type="hidden" name="item_name" value="Setup My Project">
								<input type="hidden" name="no_note" value="0">
								<input type="hidden" name="currency_code" value="USD">
								<input type="hidden" name="bn" value="PP-DonationsBF:btn_donate_LG.gif:NonHostedGuest">
								<input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
								<img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
								</form>
							</li>
							<li>
								<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
								<input type="hidden" name="cmd" value="_donations">
								<input type="hidden" name="business" value="alots.ssa@gmail.com">
								<input type="hidden" name="lc" value="US">
								<input type="hidden" name="item_name" value="Setup My Project">
								<input type="hidden" name="no_note" value="0">
								<input type="hidden" name="currency_code" value="BRL">
								<input type="hidden" name="bn" value="PP-DonationsBF:btn_donate_LG.gif:NonHostedGuest">
								<input type="image" src="https://www.paypalobjects.com/pt_BR/i/btn/btn_donate_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
								<img alt="" border="0" src="https://www.paypalobjects.com/pt_BR/i/scr/pixel.gif" width="1" height="1">
								</form>
							</li>
						</ul>
					</section>
				</div>

				<div class="wrapper style2">
					<div class="title">Current options</div>
					<div id="main" class="container">


							<section id="features">
								<header class="style1">
									<h2>The most used technologies and frameworks</h2>
									<p>a few clicks to be configured, no complications</p>
								</header>
								<div class="feature-list">
									<div class="row">
										<div class="6u 12u(mobile)">
											<section>
												<h3 class="icon fa-comment">MVC</h3>
												<p>Spring, VRaptor, JSF, Vaadin ...												
												Choose the framework  you need
												</p>
											</section>
										</div>
										<div class="6u 12u(mobile)">
											<section>
												<h3 class="icon fa-refresh">Maven</h3>
												<p>Pom file with all dependencies you need to run your project</p>
											</section>
										</div>
									</div>
									<div class="row">
										<div class="6u 12u(mobile)">
											<section>
												<h3 class="icon fa-picture-o">Addons</h3>
												<p>Spring Security, Spring JPA, PrimeFaces, JPA, CDI</p>
											</section>
										</div>
										<div class="6u 12u(mobile)">
											<section>
												<h3 class="icon fa-cog">Java version</h3>
												<p>Choose the Java version that you need</p>
											</section>
										</div>
									</div>
									<div class="row">
										<div class="6u 12u(mobile)">
											<section>
												<h3 class="icon fa-wrench">Database</h3>
												<p>MySQL, PostgreSQL, SQLServer, Oracle</p>
											</section>
										</div>
										<div class="6u 12u(mobile)">
											<section>
												<h3 class="icon fa-check">Templates</h3>
												<p>Themes to your projects</p>
											</section>
										</div>
									</div>
								</div>
								<ul class="actions actions-centered">
									<li><a href="setup" class="button style1 big">Start now</a></li>
								</ul>
							</section>

					</div>
				</div>

			<!-- Highlights -->
				<div class="wrapper style3">
					<div class="title">Available technologies</div>
					<div id="highlights" class="container">
						<div class="row 150%">
							<div class="4u 12u(mobile)">
								<section class="highlight">
									<img class="image featured" src="images/spring.png" alt="" />
									<h3>Spring</h3>
									<p>The most used Java framework around the world. Enjoy all integrations provided for the Spring universe.</p>
								</section>
							</div>
							<div class="4u 12u(mobile)">
								<section class="highlight">
									<img class="image featured" src="images/prime.png" alt="" />
									<h3>JSF</h3>
									<p>The MVC framework in JavaEE. Take advantage of the great UI extensions available in the JSF and its extensions.</p>
								</section>
							</div>
							<div class="4u 12u(mobile)">
								<section class="highlight">
									<img class="image featured" src="images/vraptor.png" alt="" />
									<h3>VRaptor</h3>
									<p>A great MVC Java framework. Built on top of CDI, VRaptor enables a very clean integration with JavaEE</p>
								</section>
							</div>
						</div>
					</div>
				</div>

			<!-- Footer -->
				<div id="footer-wrapper" class="wrapper">
					<div class="title">customize</div>
					<div id="footer" class="container">
						<header class="style1">
							<h2>Do you need something different?</h2>
							<p>								
								Let us solve your problems with setups.<br />
							</p>
						</header>
						<hr />
						<div class="row 150%">
							<div class="12u 12u(mobile)">

								<!-- Contact Form -->
									<section>
										<div>${success}</div>
										<form method="post" action="<c:url value="/contact"/>" id="setupContactForm">
											<div class="row 50%">
												<div class="6u 12u(mobile)">
													<input type="text" name="name" id="contact-name" placeholder="Nome" required="required"/>
												</div>
												<div class="6u 12u(mobile)">
													<input type="email" name="email" id="contact-email" placeholder="Email" required="required"/>
												</div>
											</div>
											<div class="row 50%">
												<div class="12u">
													<textarea name="message" id="contact-message" placeholder="Messagem" rows="4" required="required"></textarea>
												</div>
											</div>
											<div class="row">
												<div class="12u">
													<ul class="actions">
														<li><input type="submit" class="style1" value="Submit" /></li>
														<li><input type="reset" class="style2" value="Clear" /></li>
													</ul>
												</div>
											</div>
										</form>
									</section>

							</div>
							
						</div>
						<hr />
					</div>
					<div id="copyright">
						<ul>
							<li>&copy; Setup My Project</li>
						</ul>
					</div>
				</div>

		</div>

		<!-- Scripts -->

			<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
			<script src="${pageContext.request.contextPath}/js/jquery.dropotron.min.js"></script>
			<script src="${pageContext.request.contextPath}/js/skel.min.js"></script>
			<script src="${pageContext.request.contextPath}/js/skel-viewport.min.js"></script>
			<script src="${pageContext.request.contextPath}/js/util.js"></script>
			<script src="${pageContext.request.contextPath}/js/main.js"></script>
			<c:if test="${env.isProd()}">
				<script src="${pageContext.request.contextPath}/js/analytics.js"></script>
			</c:if>	

	</body>
</html>

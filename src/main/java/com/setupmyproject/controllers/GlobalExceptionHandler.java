package com.setupmyproject.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.conf.AccessEnvironment;
import com.setupmyproject.exceptions.DownloadException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private AccessEnvironment env;
	@Autowired
	private MailSender mailer;
	@Autowired
	private ThreadPoolTaskExecutor executor;

	private Logger logger = LoggerFactory
			.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ModelAndView methodNotAllowedHandler(HttpServletRequest req, Exception e)
			throws Exception {

		return new ModelAndView("errors/405");
	}	

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e)
			throws Exception {

		SimpleMailMessage email = createBaseEmail();
		email.setText(ExceptionUtils.getStackTrace(e));

		executor.execute(() -> {
			logger.error("Enviando mensagem de erro");
			mailer.send(email);
		});

		return new ModelAndView("errors/500");
	}

	@ExceptionHandler(value = DownloadException.class)
	public ModelAndView downloadErrorHandler(HttpServletRequest req,
			DownloadException e) throws Exception {

		SimpleMailMessage email = createBaseEmail();
		email.setText("Problema no download do setup " + e.getToken() + "\n\n"
				+ ExceptionUtils.getStackTrace(e.getCause()));

		executor.execute(() -> {
			logger.error("Enviando mensagem de erro");
			mailer.send(email);
		});

		return new ModelAndView("errors/500-download");
	}

	private SimpleMailMessage createBaseEmail() {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom(env.getProperty("email.from"));
		email.setTo(env.getProperty("email.errorTo"));
		email.setSubject("Novo problema no SetupMyProject :(");
		return email;
	}
}

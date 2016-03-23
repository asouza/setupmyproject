package com.setupmyproject.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.components.OngoinSetupState;
import com.setupmyproject.components.PageMessages;
import com.setupmyproject.components.TransactionalRunner;
import com.setupmyproject.conf.AccessEnvironment;
import com.setupmyproject.daos.RequestedSetupDAO;
import com.setupmyproject.downloads.DownloadableProject;
import com.setupmyproject.downloads.RequestedSetupUnlocker;
import com.setupmyproject.exceptions.DownloadException;
import com.setupmyproject.exceptions.RequestedSetupTokenAwareException;
import com.setupmyproject.exceptions.http.NotFoundStatusException;
import com.setupmyproject.exceptions.http.UnauthorizedStatusException;
import com.setupmyproject.forge.ForgeEngine;
import com.setupmyproject.forge.ProjectCreator;
import com.setupmyproject.models.RequestedSetup;
import com.setupmyproject.models.SetupState;

@Controller
@RequestMapping("/downloads")
@Transactional
public class DownloadsController {

	@Autowired
	private PageMessages pageMessages;
	@Autowired
	private RequestedSetupUnlocker requestedSetupUnlocker;
	@Autowired
	private AccessEnvironment env;
	@Autowired
	private RequestedSetupDAO requestedSetupDAO;
	@Autowired
	private TransactionalRunner transactionalRunner;
	@Autowired
	private ForgeEngine projectCreatorEngine;

	@RequestMapping(method = RequestMethod.GET, produces = "application/octet-stream")
	@ResponseBody
	public HttpEntity<byte[]> download(String token) {

		try {
			DeferredResult<DownloadableProject> result = new DeferredResult<DownloadableProject>();

			requestedSetupUnlocker.unlock(token, (requestedSetup) -> {
				new ProjectCreator(requestedSetup, result, projectCreatorEngine).run();
				return null;
			});

			DownloadableProject project = (DownloadableProject) result
					.getResult();
			return project.toHttpEnty();
		} catch (NotFoundStatusException | UnauthorizedStatusException e) {
			throw e;
		}

		catch (Exception exception) {
			throw new DownloadException(exception, token);
		}
	}

	/*
	 * adicionei o post aqui pq o link de retorno do paypal faz um post e não um
	 * get. Só deus sabe pq.
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView index(String token) {
		ModelAndView modelAndView = new ModelAndView("downloads/index");
		modelAndView.addObject("token", token);

		RequestedSetup requestedSetup = requestedSetupDAO.searchByToken(token);
		OngoinSetupState ongoinSetupState = new OngoinSetupState(
				new SetupState(requestedSetup.getBsonEncrypted()));
		modelAndView.addObject("ongoingSetupState", ongoinSetupState);
		return modelAndView;
	}
	
	@ExceptionHandler(NotFoundStatusException.class)
	public ModelAndView handleIONotFoundStatusException(
			NotFoundStatusException ex) {
		ModelAndView modelAndView = modelAndViewForExceptions(ex);
		pageMessages.info("payment.info.not_payed",
				"You need to pay before download", modelAndView);

		return modelAndView;
	}

	private ModelAndView modelAndViewForExceptions(
			RequestedSetupTokenAwareException ex) {
		ModelAndView modelAndView = new ModelAndView("redirect:payment/form");
		modelAndView.addObject("token", ex.getToken());
		return modelAndView;
	}

	@ExceptionHandler(UnauthorizedStatusException.class)
	public ModelAndView handleUnauthorizedStatusException(
			UnauthorizedStatusException ex) {
		ModelAndView modelAndView = modelAndViewForExceptions(ex);
		pageMessages.info("payment.info.invalid",
				"Your payment was not authorized", modelAndView);

		return modelAndView;
	}
}

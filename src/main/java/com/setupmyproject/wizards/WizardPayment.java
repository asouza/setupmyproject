package com.setupmyproject.wizards;

import java.util.concurrent.Callable;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.controllers.forms.ConfigurationForm;
import com.setupmyproject.daos.RequestedSetupDAO;
import com.setupmyproject.models.CommandGenerators;
import com.setupmyproject.models.RequestedSetup;
import com.setupmyproject.models.SetupState;

public class WizardPayment implements Wizard {

	private static String nextPage = "payment/index";
	private SetupState currentState;
	
	public WizardPayment(SetupState setupState) {
		this.currentState = setupState;
	}
	
	@Override
	public ModelAndView getModelAndView(ApplicationContext applicationContext) {
		try {
			
			RequestedSetupDAO requestedSetupDAO = applicationContext.getBean(RequestedSetupDAO.class);
			RequestedSetup newRequestedSetup = new RequestedSetup(currentState.getValue());
			requestedSetupDAO.save(newRequestedSetup);
			
			CommandGenerators commands = currentState.getCommandGenerators();
			return WizardPayment.formPaymentModelAndView(commands, newRequestedSetup.getGeneratedToken());
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static ModelAndView formPaymentModelAndView(CommandGenerators commands,String token){
		ModelAndView modelAndView = new ModelAndView(nextPage);
		modelAndView.addObject("commands", commands);
		modelAndView.addObject("token",token);
		modelAndView.addObject("projectType",commands.getProjectDefinition());
		return modelAndView;
	}

}

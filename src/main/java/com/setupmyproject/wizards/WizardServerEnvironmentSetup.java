package com.setupmyproject.wizards;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.controllers.forms.ServerEnvironmentAddonForm;
import com.setupmyproject.models.ServerEnvironmentAddon;

public class WizardServerEnvironmentSetup implements Wizard{

	@Override
	public ModelAndView getModelAndView(ApplicationContext applicationContext) {
		return getModelAndView(applicationContext,new ServerEnvironmentAddonForm());
	}

	public ModelAndView getModelAndView(ApplicationContext applicationContext,
			ServerEnvironmentAddonForm serverEnvironmentAddonForm) {
		ModelAndView modelAndView = new ModelAndView("addons/server-environment/step1");
		modelAndView.addObject("serverEnvironmentAddonForm", serverEnvironmentAddonForm);
		modelAndView.addObject("serverEnvironments",ServerEnvironmentAddon.values());
		return modelAndView;
	}

}

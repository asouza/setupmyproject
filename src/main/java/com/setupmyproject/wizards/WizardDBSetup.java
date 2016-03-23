package com.setupmyproject.wizards;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.controllers.forms.DBConfigurationForm;
import com.setupmyproject.models.DBType;

public class WizardDBSetup implements Wizard {

	private String nextPage = "addons/db/step1-db";

	@Override
	public ModelAndView getModelAndView(ApplicationContext applicationContext) {
		ModelAndView modelAndView = new ModelAndView(nextPage);
		modelAndView.addObject("dbAddonForm", new DBConfigurationForm());
		modelAndView.addObject("dbTypes",DBType.values());
		return modelAndView;
	}

}

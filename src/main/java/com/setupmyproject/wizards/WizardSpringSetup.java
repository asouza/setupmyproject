package com.setupmyproject.wizards;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.controllers.forms.SpringAddonForm;
import com.setupmyproject.models.SpringAddon;

public class WizardSpringSetup implements Wizard{

	private String nextPage = "projects-types/spring/step1";

	public ModelAndView getModelAndView(ApplicationContext applicationContext) {
		ModelAndView modelAndView = new ModelAndView(nextPage);
		modelAndView.addObject("addons", Arrays.asList(SpringAddon.values()));
		modelAndView.addObject("springAddonForm", new SpringAddonForm());
		return modelAndView;
	}

}

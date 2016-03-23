package com.setupmyproject.wizards;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.controllers.forms.SpringBootAddonForm;
import com.setupmyproject.models.SpringBootAddon;

public class WizardSpringBootSetup implements Wizard{

	private String nextPage = "projects-types/springboot/step1-boot";

	public ModelAndView getModelAndView(ApplicationContext applicationContext) {
		ModelAndView modelAndView = new ModelAndView(nextPage);
		modelAndView.addObject("addons", Arrays.asList(SpringBootAddon.values()));
		modelAndView.addObject("springBootAddonForm", new SpringBootAddonForm());
		return modelAndView;
	}
}

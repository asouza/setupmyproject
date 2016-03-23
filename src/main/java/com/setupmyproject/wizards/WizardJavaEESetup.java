package com.setupmyproject.wizards;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.controllers.forms.JavaEEAddonForm;
import com.setupmyproject.models.JavaEEAddon;

public class WizardJavaEESetup implements Wizard {
	
	private String nextPage = "projects-types/javaee/step1";

	@Override
	public ModelAndView getModelAndView(ApplicationContext applicationContext) {
		ModelAndView modelAndView = new ModelAndView(nextPage);
		modelAndView.addObject("addons", Arrays.asList(JavaEEAddon.values()));
		modelAndView.addObject("javaEEAddonForm", new JavaEEAddonForm());
		return modelAndView;
	}

}

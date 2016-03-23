package com.setupmyproject.wizards;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.controllers.forms.JavaConfigurationForm;
import com.setupmyproject.models.JavaVersion;

public class WizardJavaVersionSetup implements Wizard {

	private String nextPage = "projects-types/spring/step2";

	@Override
	public ModelAndView getModelAndView(ApplicationContext applicationContext) {
		ModelAndView modelAndView = new ModelAndView(nextPage);
		modelAndView.addObject("versions", JavaVersion.values());
		modelAndView.addObject("javaConfigurationForm", new JavaConfigurationForm());
		return modelAndView;
	}

}

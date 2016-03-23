package com.setupmyproject.wizards;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

/*
 * nao deveria implementar wizard nao
 */
public class LazyWizardDecision implements Wizard{

	@Override
	public ModelAndView getModelAndView(ApplicationContext applicationContext) {
		return null;
	}

	
}

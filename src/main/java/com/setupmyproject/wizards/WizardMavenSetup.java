package com.setupmyproject.wizards;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.controllers.forms.MavenSetupForm;

public class WizardMavenSetup implements Wizard{
	
	public static final String COMMAND_NAME = "mavenSetupForm";
	private String nextPage = "addons/maven/step1-maven";
	
	/**
	 * Para reaproveitar o form do maven para outro fluxo
	 * @param customPagePath
	 */
	public WizardMavenSetup(String customPagePath) {
		this.nextPage = customPagePath;
	}
	
	public WizardMavenSetup() {		
	}

	@Override
	public ModelAndView getModelAndView(ApplicationContext applicationContext) {
		return getModelAndView(new MavenSetupForm());
	}

	/**
	 * Deve ser usado para restaurar a tela, em caso de erro de validação.
	 * @param mavenSetupForm
	 * @return
	 */
	public ModelAndView getModelAndView(MavenSetupForm mavenSetupForm) {
		ModelAndView modelAndView = new ModelAndView(nextPage);
		modelAndView.addObject(COMMAND_NAME, mavenSetupForm);
		return modelAndView;
	}

}

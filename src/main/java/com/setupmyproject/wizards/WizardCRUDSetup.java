package com.setupmyproject.wizards;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.controllers.forms.CrudForm;
import com.setupmyproject.models.CRUDType;

public class WizardCRUDSetup implements Wizard{
	
	public static final String COMMAND_NAME = "crudForm";
	private String nextPage = "addons/crud/step1-crud";

	@Override
	public ModelAndView getModelAndView(ApplicationContext applicationContext) {
		return getModelAndView(new CrudForm());
	}
	
	/**
	 * Deve ser usado para restaurar a tela, em caso de erro de validação.
	 * @param crudForm
	 * @return
	 */
	public ModelAndView getModelAndView(CrudForm crudForm) {
		ModelAndView modelAndView = new ModelAndView(nextPage);
		modelAndView.addObject("crudTypes", Arrays.asList(CRUDType.values()));
		modelAndView.addObject(COMMAND_NAME, crudForm);
		return modelAndView;
	}	

}

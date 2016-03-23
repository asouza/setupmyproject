package com.setupmyproject.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.components.OngoinSetupState;
import com.setupmyproject.components.StepDiscovery;
import com.setupmyproject.controllers.forms.CrudForm;
import com.setupmyproject.controllers.forms.validators.CrudFormValidator;
import com.setupmyproject.wizards.WizardCRUDSetup;

@Controller
@Transactional
public class CrudSetupController {

	@Autowired
	private StepDiscovery stepDiscovery;
	@Autowired
	private OngoinSetupState ongoingSetupState;
	
	
	@InitBinder
	public void init(WebDataBinder dataBinder){
		dataBinder.setValidator(new CrudFormValidator());
	}

	@RequestMapping(value="/setup/crud/addons",method=RequestMethod.POST)
	public ModelAndView chooseCRUDAddon(@Valid CrudForm crudForm,BindingResult bindingResult){
		crudForm.setProjectType(ongoingSetupState.getProjectType());		
		
		return stepDiscovery.nextPage(this, crudForm,
				bindingResult,
				() -> new WizardCRUDSetup().getModelAndView(crudForm));		
	}
	
}

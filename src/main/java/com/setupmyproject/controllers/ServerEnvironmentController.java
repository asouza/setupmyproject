package com.setupmyproject.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.components.StepDiscovery;
import com.setupmyproject.controllers.forms.ServerEnvironmentAddonForm;
import com.setupmyproject.wizards.WizardServerEnvironmentSetup;

@Controller
@Transactional
public class ServerEnvironmentController {

	@Autowired
	private StepDiscovery stepDiscovery;
	@Autowired
	private ApplicationContext applicationContext;

	@RequestMapping(value="/server/environment/addons",method=RequestMethod.POST)
	public ModelAndView chooseServer(ServerEnvironmentAddonForm serverEnvironmentAddonForm,BindingResult bindingResult){
		return stepDiscovery.nextPage(this,serverEnvironmentAddonForm,bindingResult,() -> {
			return new WizardServerEnvironmentSetup().getModelAndView(applicationContext,serverEnvironmentAddonForm);
		});		
	}
}

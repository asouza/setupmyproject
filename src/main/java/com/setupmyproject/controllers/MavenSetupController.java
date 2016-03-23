package com.setupmyproject.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.components.StepDiscovery;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.wizards.WizardMavenSetup;

@Controller
@Transactional
public class MavenSetupController {

	@Autowired
	private StepDiscovery stepDiscovery;

	@RequestMapping(value = "/setup/maven", method = RequestMethod.POST)
	public ModelAndView choose(@Valid MavenSetupForm mavenForm,
			BindingResult bindingResult) {
		return stepDiscovery.nextPage(this, mavenForm,
				bindingResult,
				() -> new WizardMavenSetup().getModelAndView(mavenForm));
	}
}

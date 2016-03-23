package com.setupmyproject.controllers.existingproject;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.components.OngoinSetupState;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.wizards.WizardCRUDSetup;
import com.setupmyproject.wizards.WizardMavenSetup;

@Controller
@Transactional
public class ExistingProjectMavenSetupController {

	@Autowired
	private OngoinSetupState ongoinSetupState;
	@Autowired
	private ApplicationContext applicationContext;

	@RequestMapping(value = "/existing/project/maven", method = RequestMethod.POST)
	public ModelAndView choose(@Valid MavenSetupForm mavenForm,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {			
			ModelAndView modelAndView = new WizardMavenSetup(
					"projects-types/existing-project/step2-existing-project")
					.getModelAndView(mavenForm);
			ongoinSetupState.updateSetupStateInRequest();
			return modelAndView;
		}
		ongoinSetupState.updateStateWithStepForm(mavenForm);
		return new WizardCRUDSetup().getModelAndView(applicationContext);
		
		
		
		
		
	}
		
}

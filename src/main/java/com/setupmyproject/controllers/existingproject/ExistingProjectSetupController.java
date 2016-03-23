package com.setupmyproject.controllers.existingproject;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.components.OngoinSetupState;
import com.setupmyproject.controllers.forms.ExistingConfigurationForm;
import com.setupmyproject.models.ProjectType;
import com.setupmyproject.models.SetupState;
import com.setupmyproject.wizards.WizardMavenSetup;

@Controller
public class ExistingProjectSetupController {
	
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private OngoinSetupState ongoingSetupState;
	
	@RequestMapping(value = "/existing/setup", name = "existingSetupWizard",method=RequestMethod.GET)
	public ModelAndView form() {
		ExistingConfigurationForm configurationForm = new ExistingConfigurationForm();
		ModelAndView modelAndView = formModelAndView(configurationForm);
		return modelAndView;
	}

	private ModelAndView formModelAndView(
			ExistingConfigurationForm configurationForm) {
		ModelAndView modelAndView = new ModelAndView("projects-types/existing-project/step1-existing-project");
		modelAndView.addObject("existingConfigurationForm", configurationForm);
		modelAndView.addObject("projectTypes", Arrays.asList(
				ProjectType.SPRING, ProjectType.SPRING_BOOT, ProjectType.JSF,
				ProjectType.VRAPTOR));
		return modelAndView;
	}
	
	@RequestMapping(value = "/existing/setup", name = "choosenExistingSetupWizard",method=RequestMethod.POST)
	public ModelAndView chooseExistingSetup(@Valid ExistingConfigurationForm existingConfigurationForm,BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			 return formModelAndView(existingConfigurationForm);
		}
		
		ModelAndView page = new WizardMavenSetup(
				"projects-types/existing-project/step2-existing-project")
				.getModelAndView(applicationContext);
		SetupState setupState = new SetupState();
		
		setupState.addConf(existingConfigurationForm,(state) -> {
			ongoingSetupState.updateState(state);
			return null;
		});
		
		return page;
	}	
	
	
}

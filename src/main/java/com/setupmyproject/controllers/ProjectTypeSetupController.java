package com.setupmyproject.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.components.OngoinSetupState;
import com.setupmyproject.controllers.forms.ConfigurationForm;
import com.setupmyproject.models.ProjectType;
import com.setupmyproject.models.SetupState;

@Controller
@Transactional
public class ProjectTypeSetupController {
	
	@Autowired
	private ApplicationContext context;
	@Autowired	
	private OngoinSetupState ongoingSetupState;
	
	@RequestMapping(value="/setup",name="startWizard")
	public ModelAndView index(){
		ConfigurationForm configurationForm = new ConfigurationForm();
		ModelAndView modelAndView = formModelAndView(configurationForm);
		return modelAndView;
	}

	private ModelAndView formModelAndView(ConfigurationForm configurationForm) {
		ModelAndView modelAndView = new ModelAndView("setup");
		modelAndView.addObject("configurationForm", configurationForm);
		modelAndView.addObject("projectTypes",ProjectType.values());
		return modelAndView;
	}
	
	@RequestMapping("/setup/projectType")
	public ModelAndView chooseType(@Valid ConfigurationForm configurationForm,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return formModelAndView(configurationForm);
		}
		
		ProjectType projectType = configurationForm.getProjectType();
		SetupState setupState = new SetupState();		
		ModelAndView page = projectType.nextPage(ProjectTypeSetupController.class, context,setupState);		
		page.addObject("setupState",setupState.addConf(configurationForm,(state) -> {
			ongoingSetupState.updateState(state);
			return null;
		}));		
		return page;
	}
	
}

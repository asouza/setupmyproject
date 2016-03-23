package com.setupmyproject.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.controllers.forms.SetupTypeForm;
import com.setupmyproject.models.SetupType;

/**
 * Define se é um novo projeto projeto, ou um em andamento
 * @author alberto
 *
 */
@Controller
public class SetupTypeController {

	@Autowired
	private ApplicationContext context;
	
	@RequestMapping(value="/setup/type",name="setupTypeAction",method=RequestMethod.GET)
	public ModelAndView index(){
		SetupTypeForm setupTypeForm = new SetupTypeForm();
		ModelAndView modelAndView = formModelAndView(setupTypeForm);
		return modelAndView;
	}	
	
	private ModelAndView formModelAndView(SetupTypeForm setupTypeForm) {
		ModelAndView modelAndView = new ModelAndView("setup-type/index");
		modelAndView.addObject("setupTypeForm", setupTypeForm);
		modelAndView.addObject("setupTypes",SetupType.values());
		return modelAndView;
	}	
	
	@RequestMapping(value="/setup/type",method=RequestMethod.POST)
	public ModelAndView chooseType(@Valid SetupTypeForm setupTypeForm,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return formModelAndView(setupTypeForm);
		}
		
		SetupType setupType = setupTypeForm.getSetupType();
		return setupType.getNextPage();
	}	
}

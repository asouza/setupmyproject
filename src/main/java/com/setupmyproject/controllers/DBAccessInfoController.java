package com.setupmyproject.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.components.StepDiscovery;
import com.setupmyproject.controllers.forms.DBAccesInfoForm;

@Controller
@Transactional
public class DBAccessInfoController {

	@Autowired
	private StepDiscovery stepDiscovery;

	@RequestMapping(value="/setup/db/info",method=RequestMethod.POST)
	public ModelAndView chooseDBAddons(DBAccesInfoForm dbAccesInfoForm){
		return stepDiscovery.nextPage(this,dbAccesInfoForm);
	}
}

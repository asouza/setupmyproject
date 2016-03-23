package com.setupmyproject.controllers.vraptor;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.components.StepDiscovery;
import com.setupmyproject.controllers.forms.VRaptorAddonForm;

@Controller
@Transactional
public class VRaptorSetupController {
	
	@Autowired
	private StepDiscovery stepDiscovery;

	@RequestMapping(value="/setup/vraptor/addons",method=RequestMethod.POST)
	public ModelAndView chooseSpringAddons(VRaptorAddonForm vraptorAddonForm){
		return stepDiscovery.nextPage(this,vraptorAddonForm);		
	}
}

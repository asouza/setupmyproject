package com.setupmyproject.wizards.vraptor;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.controllers.forms.VRaptorAddonForm;
import com.setupmyproject.models.VRaptorAddon;
import com.setupmyproject.wizards.Wizard;

public class WizardVRaptorSetup implements Wizard {
	
	private String nextPage = "projects-types/vraptor/step1";

	@Override
	public ModelAndView getModelAndView(ApplicationContext applicationContext) {
		ModelAndView modelAndView = new ModelAndView(nextPage);
		modelAndView.addObject("addons", VRaptorAddon.values());
		modelAndView.addObject("vraptorAddonsForm",new VRaptorAddonForm());
		return modelAndView;
	}

}

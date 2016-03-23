package com.setupmyproject.wizards;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.controllers.forms.DeltaSpikeAddonForm;
import com.setupmyproject.models.DeltaSpikeAddon;

public class WizardDeltaSpike implements Wizard{
	
	public static final String DELTA_SPIKE_ADDON_FORM_PARAMETER_NAME = "deltaSpikeAddonForm";
	private String nextPage = "addons/deltaspike/step1-deltaspike";

	@Override
	public ModelAndView getModelAndView(ApplicationContext applicationContext) {
		ModelAndView modelAndView = new ModelAndView(nextPage);
		modelAndView.addObject("addons", Arrays.asList(DeltaSpikeAddon.values()));
		modelAndView.addObject(DELTA_SPIKE_ADDON_FORM_PARAMETER_NAME, new DeltaSpikeAddonForm());
		return modelAndView;
		
	}

}

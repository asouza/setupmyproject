package com.setupmyproject.wizards;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

public interface Wizard {

	ModelAndView getModelAndView(ApplicationContext applicationContext);
}

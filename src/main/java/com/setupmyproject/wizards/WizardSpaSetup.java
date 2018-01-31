package com.setupmyproject.wizards;

import com.setupmyproject.controllers.forms.SpaOptionForm;
import com.setupmyproject.models.SpaOption;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

public class WizardSpaSetup implements Wizard {


    private String nextPage = "projects-types/spa/step1";

    @Override
    public ModelAndView getModelAndView(ApplicationContext applicationContext) {
        ModelAndView view = new ModelAndView(nextPage);

        view.addObject("options", Arrays.asList(SpaOption.values()));
        view.addObject("spaOptionForm", new SpaOptionForm());

        return view;
    }

}

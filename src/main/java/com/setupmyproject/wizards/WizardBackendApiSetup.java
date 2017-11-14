package com.setupmyproject.wizards;

import com.setupmyproject.controllers.forms.BackendApiForm;
import com.setupmyproject.models.BackendApi;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

public class WizardBackendApiSetup implements Wizard {

    private String nextPage = "projects-types/spa/step2";

    @Override
    public ModelAndView getModelAndView(ApplicationContext applicationContext) {
        ModelAndView view = new ModelAndView(nextPage);

        view.addObject("options", Arrays.asList(BackendApi.values()));
        view.addObject("backendApiForm", new BackendApiForm());

        return view;
    }
}

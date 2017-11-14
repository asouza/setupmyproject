package com.setupmyproject.controllers;

import com.setupmyproject.components.StepDiscovery;
import com.setupmyproject.controllers.forms.BackendApiForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@Transactional
public class BackendApiController {
    @Autowired
    private StepDiscovery stepDiscovery;

    @RequestMapping(value="/setup/spa/backend",method= RequestMethod.POST)
    public ModelAndView chooseSpringAddons(BackendApiForm form){
        return stepDiscovery.nextPage(this,form);
    }
}

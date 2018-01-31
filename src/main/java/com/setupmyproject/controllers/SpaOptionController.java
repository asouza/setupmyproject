package com.setupmyproject.controllers;

import com.setupmyproject.components.StepDiscovery;
import com.setupmyproject.controllers.forms.SpaOptionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@Transactional
public class SpaOptionController {

    @Autowired
    private StepDiscovery stepDiscovery;

    @RequestMapping(value="/setup/spa/options",method= RequestMethod.POST)
    public ModelAndView chooseSpringAddons(SpaOptionForm form){
        return stepDiscovery.nextPage(this,form);
    }
}

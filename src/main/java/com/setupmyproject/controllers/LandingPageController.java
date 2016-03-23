package com.setupmyproject.controllers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.setupmyproject.components.PageMessages;
import com.setupmyproject.models.SetupContact;

@Controller
@Transactional
public class LandingPageController {
	
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private PageMessages pageMessages;
	
	@RequestMapping("/")
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}
	
	@RequestMapping(value="/contact",method=RequestMethod.POST)
	public ModelAndView newContact(SetupContact setupContact,RedirectAttributes redirectAttributes){
		entityManager.persist(setupContact);
		pageMessages.success("site_contact.sucess", "Thank you! Let's setup your project", redirectAttributes);
		return new ModelAndView("redirect:/");
	}
}

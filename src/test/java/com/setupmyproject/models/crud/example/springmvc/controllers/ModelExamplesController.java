package com.setupmyproject.models.crud.example.springmvc.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.models.crud.example.springmvc.daos.ModelExampleDao;
import com.setupmyproject.models.crud.example.springmvc.models.ModelExample;

@Controller
@RequestMapping("/products")
@Transactional
public class ModelExamplesController {
	
	@Autowired
	private ModelExampleDao modelExampleDao;

	@RequestMapping("/form")
	public String form(ModelExample example){
		return "prodcuts/form";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String save(@Valid ModelExample modelExample,BindingResult bindinResult){
		if(bindinResult.hasErrors()){
			return form(modelExample);
		}
		modelExampleDao.save(modelExample);
		return "redirect:products/list";
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	public ModelAndView load(@PathVariable("id") Integer id){
		ModelAndView modelAndView = new ModelAndView("products/form");
		modelAndView.addObject("modelExample", modelExampleDao.findById(id));
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView modelAndView = new ModelAndView("products/list");
		modelAndView.addObject("list", modelExampleDao.all());
		return modelAndView;
	}
	
	//just because get is easier here. Be my guess if you want to change.
	@RequestMapping(method=RequestMethod.GET,value="/remove/{id}")
	public String remove(ModelExample modelExample){
		modelExampleDao.remove(modelExample);
		return "redirect:products/list";
	}
}

package com.setupmyproject.models.crud.example.vraptor.controllers;

import javax.inject.Inject;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

import com.setupmyproject.models.crud.example.springmvc.daos.ModelExampleDao;
import com.setupmyproject.models.crud.example.springmvc.models.ModelExample;

@Controller
@Path("/products")
public class ModelExamplesController {
	
	@Inject
	private ModelExampleDao modelExampleDao;
	@Inject
	private Validator validator;
	@Inject
	private Result result;

	@Get("/form")
	public void formAdd(ModelExample example){
		result.include("example", example);
	}
	
	@Post("")
	public void save(@Valid ModelExample modelExample){
		validator.onErrorForwardTo(ModelExamplesController.class).formAdd(modelExample);
		modelExampleDao.save(modelExample);
		result.redirectTo(ModelExamplesController.class).list();
	}
	
	
	@Get("/{id}")
	public void formUpdate(Integer id){
		result.include("modelExample", modelExampleDao.findById(id));
	}
	
	@Get("")
	public void list(){
		result.include("list", modelExampleDao.all());
	}
	
	//just because get is easier here. Be my guess if you want to change.
	@Get("/remove/{id}")
	public void remove(Integer id){
		ModelExample modelExample = modelExampleDao.findById(id);
		modelExampleDao.remove(modelExample);
		result.redirectTo(ModelExamplesController.class).list();
	}
	
	public void update(Integer id,ModelExample modelExample){
		modelExample.setId(id);
		validator.onErrorForwardTo(ModelExamplesController.class).formUpdate(id);
	}
}

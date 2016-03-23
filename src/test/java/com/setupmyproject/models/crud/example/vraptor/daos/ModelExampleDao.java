package com.setupmyproject.models.crud.example.vraptor.daos;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.setupmyproject.models.crud.example.springmvc.models.ModelExample;

public class ModelExampleDao {
	
	@Inject
	private EntityManager manager;

	public List<ModelExample> all() {
		return manager.createQuery("select p from Product p",ModelExample.class).getResultList();
	}

	public void save(ModelExample modelExample) {
		manager.persist(modelExample);
	}

	public ModelExample findById(Integer id) {
		return manager.find(ModelExample.class, id);
	}

	public void remove(ModelExample modelExample) {
		manager.remove(modelExample);
	}

}

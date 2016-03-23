package com.setupmyproject.models.crud.example.springmvc.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.setupmyproject.models.crud.example.springmvc.models.ModelExample;

@Repository
public class ModelExampleDao {
	
	@PersistenceContext
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

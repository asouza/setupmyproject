package com.setupmyproject.controllers.forms;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.crud.CrudGeneratorCommand;
import com.setupmyproject.models.CRUDType;
import com.setupmyproject.models.ProjectType;

public class CrudFormTest {

	@Test
	public void shouldReturnCrudCommandIfCrudIsSelected(){
		CrudForm crudForm = new CrudForm();
		crudForm.setProjectType(ProjectType.JSF);
		crudForm.setCrudType(CRUDType.CRUD_PRODUCT);
		
		List<? extends ProjectCommand> commands = crudForm.createComand();		
		Assert.assertEquals(1, commands.size());
		Assert.assertEquals(CrudGeneratorCommand.class, commands.get(0).getClass());
	}
	
	@Test
	public void shouldReturnEmptyListForNonCrud(){
		CrudForm crudForm = new CrudForm();
		crudForm.setProjectType(ProjectType.JSF);
		
		List<? extends ProjectCommand> commands = crudForm.createComand();		
		Assert.assertEquals(0, commands.size());
	}
}

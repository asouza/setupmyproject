package com.setupmyproject.models;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.setupmyproject.controllers.forms.ConfigurationForm;
import com.setupmyproject.controllers.forms.CrudForm;
import com.setupmyproject.controllers.forms.DBConfigurationForm;
import com.setupmyproject.controllers.forms.ExistingConfigurationForm;
import com.setupmyproject.controllers.forms.JavaConfigurationForm;
import com.setupmyproject.controllers.forms.SpringAddonForm;
import com.setupmyproject.models.crud.AlreadyCreatedCrudProjectDefinition;

public class SetupStateTest {
	
	private SetupState setupState;
	private ConfigurationForm configurationForm;
	private SpringAddonForm springAddonForm;
	private DBConfigurationForm dbConfigurationForm;
	private JavaConfigurationForm javaConfigurationForm;

	@Before
	public void setup(){
		setupState = new SetupState();
		
		springAddonForm = new SpringAddonForm();
		springAddonForm.setAddons(Arrays.asList(SpringAddon.SPRING_JPA,SpringAddon.SPRING_JSP));
		setupState.addConf(springAddonForm,(s) -> {return null;});
		
		javaConfigurationForm = new JavaConfigurationForm();
		javaConfigurationForm.setVersion(JavaVersion.JAVA8);
		setupState.addConf(javaConfigurationForm,(s) -> {return null;});
		
		dbConfigurationForm = new DBConfigurationForm();
		dbConfigurationForm.setDbType(DBType.MYSQL5);
		setupState.addConf(dbConfigurationForm,(s) -> {return null;});
		
	}
	
	@Test
	public void shouldRecoverState(){
		configurationForm = new ConfigurationForm();
		configurationForm.setProjectType(ProjectType.SPRING);
		setupState.addConf(configurationForm,(s) -> {return null;});
		
		String encrypted = setupState.getValue();
		SetupState recoveryState = new SetupState(encrypted);
		
		CommandGenerators commands = recoveryState.getCommandGenerators();
		Assert.assertEquals(ConfigurationForm.class,commands.find(ConfigurationForm.class).getClass());
		Assert.assertEquals(SpringAddonForm.class,commands.find(SpringAddonForm.class).getClass());
		Assert.assertEquals(DBConfigurationForm.class,commands.find(DBConfigurationForm.class).getClass());
		Assert.assertEquals(JavaConfigurationForm.class,commands.find(JavaConfigurationForm.class).getClass());
	}
	
	@Test
	public void shouldRecoverStateWithAlreadyCreatedCrudProjectDefinition() {
		SetupState alreadyCreatedProjectSetup = new SetupState();
		
		ExistingConfigurationForm existingConfigurationForm = new ExistingConfigurationForm();
		existingConfigurationForm.setProjectType(ProjectType.SPRING);
		alreadyCreatedProjectSetup.addConf(existingConfigurationForm,(s) -> {return null;});
		
		CrudForm crudForm = new CrudForm();
		crudForm.setCrudType(CRUDType.CRUD_PRODUCT);
		crudForm.setProjectType(existingConfigurationForm.getProjectDefinition());
		alreadyCreatedProjectSetup.addConf(crudForm,(s) -> {return null;});
		
				
		String encrypted = alreadyCreatedProjectSetup.getValue();
		SetupState recoveryState = new SetupState(encrypted);
	
		CommandGenerators commands = recoveryState.getCommandGenerators();
		Assert.assertEquals(ExistingConfigurationForm.class,commands.find(ExistingConfigurationForm.class).getClass());
		Assert.assertEquals(CrudForm.class,commands.find(CrudForm.class).getClass());
	}
	
	@Test
	public void shouldExecuteCreationProjectSteps(){
		configurationForm = new ConfigurationForm();
		configurationForm.setProjectType(ProjectType.SPRING);
		setupState.addConf(configurationForm,(s) -> {return null;});
		
		CommandGenerators commands = setupState.getCommandGenerators();
		Assert.assertEquals(ConfigurationForm.class,commands.find(ConfigurationForm.class).getClass());
		Assert.assertEquals(SpringAddonForm.class,commands.find(SpringAddonForm.class).getClass());
		Assert.assertEquals(DBConfigurationForm.class,commands.find(DBConfigurationForm.class).getClass());
		Assert.assertEquals(JavaConfigurationForm.class,commands.find(JavaConfigurationForm.class).getClass());
	}
	
	
	@Test
	public void shouldGetProjectType(){
		configurationForm = new ConfigurationForm();
		configurationForm.setProjectType(ProjectType.SPRING);
		setupState.addConf(configurationForm,(s) -> {return null;});
		
		ProjectDefinition projectType = setupState.getProjectType();
		Assert.assertEquals(ProjectType.SPRING, projectType);
	}

	
	@Test(expected=IllegalStateException.class)
	public void shouldNotAcceptMoreThanOneHasProjectDefinition(){
		ExistingConfigurationForm existingConfigurationForm = new ExistingConfigurationForm();
		existingConfigurationForm.setProjectType(ProjectType.SPRING);
		setupState.addConf(existingConfigurationForm,(s) -> {return null;});
		
		configurationForm = new ConfigurationForm();
		configurationForm.setProjectType(ProjectType.SPRING);
		setupState.addConf(configurationForm,(s) -> {return null;});		
		
		setupState.getProjectType();
	}	
	
}

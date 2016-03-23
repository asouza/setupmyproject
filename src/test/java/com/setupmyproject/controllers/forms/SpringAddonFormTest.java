package com.setupmyproject.controllers.forms;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.setupmyproject.commands.EmptyCommand;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.models.SpringAddon;

public class SpringAddonFormTest {

	@Test
	public void shouldReturnCompositeCommandIfAddonChoosed(){
		SpringAddonForm springAddonForm = new SpringAddonForm();
		springAddonForm.getAddons().add(SpringAddon.SPRING_JPA);
		
		List<? extends ProjectCommand> commands = springAddonForm.createComand();
		
		Assert.assertEquals(Arrays.asList(SpringAddon.SPRING_JPA), commands);
	}
	
	@Test
	public void shouldReturnEmptyCommandIfAddonsAreEmpty(){
		SpringAddonForm springAddonForm = new SpringAddonForm();
		List<? extends ProjectCommand> commands = springAddonForm.createComand();
		Assert.assertEquals(EmptyCommand.class, commands.get(0).getClass());
	}
	
	@Test
	public void shouldReturnEmptyCommandIfAddonsAreNull(){
		SpringAddonForm springAddonForm = new SpringAddonForm();
		springAddonForm.setAddons(null);
		List<? extends ProjectCommand> commands = springAddonForm.createComand();
		Assert.assertEquals(EmptyCommand.class, commands.get(0).getClass());
	}
}

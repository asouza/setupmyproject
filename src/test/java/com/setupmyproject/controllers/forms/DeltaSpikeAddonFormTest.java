package com.setupmyproject.controllers.forms;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.deltaspike.DeltaSpikeSetupCommand;
import com.setupmyproject.models.DeltaSpikeAddon;

public class DeltaSpikeAddonFormTest {

	@Test
	public void shouldNotAddAnyDeltaSpikeCommand(){
		DeltaSpikeAddonForm deltaSpikeAddonForm = new DeltaSpikeAddonForm();
		List<? extends ProjectCommand> commands = deltaSpikeAddonForm.createComand();
		Assert.assertEquals(0, commands.size());
	}
	
	@Test
	public void shouldReturnBothDeltaSpikeCommands(){
		DeltaSpikeAddonForm deltaSpikeAddonForm = new DeltaSpikeAddonForm();
		deltaSpikeAddonForm.getAddons().add(DeltaSpikeAddon.DELTA_SPIKE_JPA);
		List<? extends ProjectCommand> commands = deltaSpikeAddonForm.createComand();
		Assert.assertEquals(2, commands.size());
		Assert.assertTrue(commands.contains(DeltaSpikeAddon.DELTA_SPIKE_JPA));
		Assert.assertTrue(commands.get(1).getClass().equals(DeltaSpikeSetupCommand.class));
	}
}

package com.setupmyproject.controllers.forms;

import java.util.ArrayList;
import java.util.List;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.deltaspike.DeltaSpikeSetupCommand;
import com.setupmyproject.models.DeltaSpikeAddon;

public class DeltaSpikeAddonForm implements CommandGenerator{

	private List<DeltaSpikeAddon> addons = new ArrayList<>();

	public List<DeltaSpikeAddon> getAddons() {
		return addons;
	}

	public void setAddons(List<DeltaSpikeAddon> addons) {
		this.addons = addons;
	}

	@Override
	public List<? extends ProjectCommand> createComand() {		
		List<ProjectCommand> commands = new ArrayList<>();
		if(!addons.isEmpty()){
			commands.addAll(addons);
			commands.add(new DeltaSpikeSetupCommand());
		}
		return commands;
	}

}

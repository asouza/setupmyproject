package com.setupmyproject.controllers.forms;

import java.util.ArrayList;
import java.util.List;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.models.SpringAddon;

public class SpringAddonForm implements CommandGenerator{

	private List<SpringAddon> addons = new ArrayList<>();

	public List<SpringAddon> getAddons() {
		return addons;
	}

	public void setAddons(List<SpringAddon> addons) {
		this.addons = addons;
	}

	@Override
	public String toString() {
		return "SpringAddonForm [addons=" + addons + "]";
	}

	@Override
	public List<? extends ProjectCommand> createComand() {
		return MaybeEmptyCommand.generate(addons);
	}
	
	

}

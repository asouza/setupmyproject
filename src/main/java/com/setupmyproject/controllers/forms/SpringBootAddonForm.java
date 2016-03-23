package com.setupmyproject.controllers.forms;

import java.util.ArrayList;
import java.util.List;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.models.SpringBootAddon;

public class SpringBootAddonForm implements CommandGenerator {

	private List<SpringBootAddon> addons = new ArrayList<>();

	public List<SpringBootAddon> getAddons() {
		return addons;
	}

	public void setAddons(List<SpringBootAddon> addons) {
		this.addons = addons;
	}

	@Override
	public String toString() {
		return "SpringBootAddonForm [addons=" + addons + "]";
	}

	@Override
	public List<? extends ProjectCommand> createComand() {
		return MaybeEmptyCommand.generate(addons);
	}

}

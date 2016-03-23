package com.setupmyproject.controllers.forms;

import java.util.ArrayList;
import java.util.List;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.models.JavaEEAddon;

public class JavaEEAddonForm implements CommandGenerator {

	List<JavaEEAddon> addons = new ArrayList<JavaEEAddon>();

	public void setAddons(List<JavaEEAddon> addons) {
		this.addons = addons;
	}

	public List<JavaEEAddon> getAddons() {
		return addons;
	}

	@Override
	public List<? extends ProjectCommand> createComand() {
		return MaybeEmptyCommand.generate(addons);
	}

}

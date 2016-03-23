package com.setupmyproject.controllers.forms;

import java.util.ArrayList;
import java.util.List;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.models.VRaptorAddon;

public class VRaptorAddonForm implements CommandGenerator {

	private List<VRaptorAddon> addons = new ArrayList<>();

	public List<VRaptorAddon> getAddons() {
		return addons;
	}

	public void setAddons(List<VRaptorAddon> addons) {
		this.addons = addons;
	}

	@Override
	public List<? extends ProjectCommand> createComand() {
		return MaybeEmptyCommand.generate(addons);
	}

}

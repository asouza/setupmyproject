package com.setupmyproject.controllers.forms;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.models.ServerEnvironmentAddon;

public class ServerEnvironmentAddonForm implements CommandGenerator{

	@NotNull
	private ServerEnvironmentAddon environmentAddon = ServerEnvironmentAddon.SERVLET_CONTAINER_3_X;

	public ServerEnvironmentAddon getEnvironmentAddon() {
		return environmentAddon;
	}

	public void setEnvironmentAddon(ServerEnvironmentAddon environmentAddon) {
		this.environmentAddon = environmentAddon;
	}

	@Override
	public List<? extends ProjectCommand> createComand() {		
		return Lists.newArrayList(environmentAddon);
	}
	
	
}

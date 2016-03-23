package com.setupmyproject.commands.existingproject;


import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.commands.UserInvisible;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.ProjectType;

@UserInvisible
public class ExistingProjectSetupCommand implements ProjectCommand{

	private ProjectType projectType;
	private static Logger logger = LoggerFactory.getLogger(ExistingProjectSetupCommand.class);

	public ExistingProjectSetupCommand(ProjectType projectType) {
		this.projectType = projectType;		
	}

	@Override
	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		
		logger.info("Gerando configuração para projeto existente do tipo "+projectType);
		
	}

	@Override
	public BigDecimal getPrice() {
		return BigDecimal.ZERO;
	}

	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(0, TimeUnit.MINUTES);
	}

}

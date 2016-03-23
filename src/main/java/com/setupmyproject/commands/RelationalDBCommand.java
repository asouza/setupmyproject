package com.setupmyproject.commands;

import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.forge.ForceDependencyInstaller;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.DBType;

public class RelationalDBCommand {

	private DBType dbType;
	private Logger logger = LoggerFactory.getLogger(RelationalDBCommand.class);

	public RelationalDBCommand(DBType dbType) {
		this.dbType = dbType;
	}

	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		
		logger.info("Gerando configuracao do maven para o banco de dados");
		
		ForceDependencyInstaller installer = new ForceDependencyInstaller(
				addonRegistry.getServices(DependencyInstaller.class).get(),
				project);
		
		DependencyBuilder depedencyBuilder = dbType.getDepedencyBuilder();
		installer.forceInstall(depedencyBuilder);		
	}

}

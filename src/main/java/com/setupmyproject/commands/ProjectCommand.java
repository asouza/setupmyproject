package com.setupmyproject.commands;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import com.setupmyproject.models.CommandGenerators;
import com.setupmyproject.models.CommandGeneratorsQuery;


public interface ProjectCommand {

	public void execute(Project project,AddonRegistry addonRegistry,CommandGeneratorsQuery commandGenerators);
	
	/**
	 * 
	 * Eu devo ser executado antes desses comandos.
	 * @param commandGenerators
	 * @return Lista de comandos
	 */
	default public List<ProjectCommand> before(CommandGenerators commandGenerators) {
		return Arrays.asList(new DefaultAfterCommand());
	}
	
	
	/**
	 * Eu devo ser executado depois desses comandos.
	 * @param commandGenerators
	 * @return Lista de comandos
	 */	
	default public List<ProjectCommand> after(CommandGenerators commandGenerators) {
		return Arrays.asList(new DefaultBeforeCommand());
	}

	public BigDecimal getPrice();	
	
	default String getNameKey(){
		return "option."+this.getClass().getSimpleName();
	}

	public TimeToExecute getTimeToExecute();
	
	
}

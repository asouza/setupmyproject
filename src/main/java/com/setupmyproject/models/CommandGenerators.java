package com.setupmyproject.models;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.commands.TopologicalSortedProjectCommandsRegistry;
import com.setupmyproject.commands.messages.JPAMessages;
import com.setupmyproject.commands.messages.ProjectCommandMessage;
import com.setupmyproject.controllers.forms.CommandGenerator;

public class CommandGenerators implements CommandGeneratorsQuery {

	private Map<Class<? extends CommandGenerator>, CommandGenerator> commandGeneratorsMap = new LinkedHashMap<>();
	private Map<Class<? extends ProjectCommand>, ProjectCommand> projectCommandsMap = new LinkedHashMap<>();
	private TopologicalSortedProjectCommandsRegistry graph = new TopologicalSortedProjectCommandsRegistry();
	private List<ProjectCommand> projectCommandsToExecute;
	private Multimap<ProjectCommandMessage, Object> projectCommandMessages = HashMultimap.create();

	public CommandGenerators(Set<CommandGenerator> commands) {
		for (CommandGenerator commandGenerator : commands) {
			this.commandGeneratorsMap.put(commandGenerator.getClass(),
					commandGenerator);
			List<? extends ProjectCommand> projectCommands = commandGenerator
					.createComand();
			if (projectCommands == null) {
				throw new IllegalStateException("O generator "
						+ commandGenerator + " está gerando um comando nulo");
			}

			for (ProjectCommand projectCommand : projectCommands) {
				this.projectCommandsMap.put(projectCommand.getClass(),projectCommand);
			}

		}
		
		this.projectCommandsToExecute = graph.register(this,
				projectCommandsMap.values()).all();

	}

	@SuppressWarnings("unchecked")
	public <T extends CommandGenerator> T find(Class<T> commandClass) {
		return (T) commandGeneratorsMap.get(commandClass);
	}

	public void executeAll(Project project, AddonRegistry addonRegistry) {
		projectCommandsToExecute.stream().forEach(
				(command) -> command.execute(project, addonRegistry, this));

	}
	

	@SuppressWarnings("unchecked")
	public <T extends ProjectCommand> T findProjectCommand(Class<T> key) {
		return (T) projectCommandsMap.get(key);
	}

	public List<ProjectCommand> getCommands() {
		return projectCommandsToExecute;
	}

	public BigDecimal getFinalPrice() {
		BigDecimal price = projectCommandsToExecute.stream()
				.map((command) -> command.getPrice())
				.reduce(BigDecimal.ZERO, (amount, next) -> amount.add(next));
		return price;
	}
	
	public TimeToExecute getFinalTime() {
		long finalTime = projectCommandsToExecute.stream()
				.map((command) -> command.getTimeToExecute().minutes())
				.reduce(0l, (amount, next) -> amount + next);
		return new TimeToExecute((int)finalTime, TimeUnit.MINUTES);
	}

	/**
	 * 
	 * @return retorna o {@link CommandGenerator} que define o tipo do projeto
	 */
	public ProjectDefinition getProjectDefinition() {
		Class<? extends HasProjectTypeDefinition> formWithProjectDefinition = HasProjectDefinitionFinder.findThroughInstances(commandGeneratorsMap.values());
		return ((HasProjectTypeDefinition) find(formWithProjectDefinition)).getProjectDefinition();
	}

	@Override
	public void messageToProjectCommand(ProjectCommandMessage messageType, Object content) {
		projectCommandMessages.put(messageType,content);
	}

	@Override
	public Collection<Object> consumeMessage(ProjectCommandMessage key) {
		return projectCommandMessages.get(key);
	}	

}

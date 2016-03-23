package com.setupmyproject.commands;

import java.util.Collection;
import java.util.List;

import com.setupmyproject.models.CommandGenerators;

public class TopologicalSortedProjectCommandsRegistry {

	private Graph<ProjectCommand> set = new Graph<>();

	public List<ProjectCommand> all() {
		return set.topologicalOrder();
	}

	public TopologicalSortedProjectCommandsRegistry register(CommandGenerators commandGenerators,Collection<ProjectCommand> projectCommands) {
		for (ProjectCommand projectCommand : projectCommands) {
				addEdges(projectCommand, projectCommand.before(commandGenerators), projectCommand.after(commandGenerators));
		}
		return this;
	}
	

	private void addEdges(ProjectCommand projectCommand,
			Collection<ProjectCommand> befores,
			Collection<ProjectCommand> afters) {
		set.addEdges(projectCommand, befores);
	
		for (ProjectCommand other : afters) {
			set.addEdge(other, projectCommand);
		}
	}
}

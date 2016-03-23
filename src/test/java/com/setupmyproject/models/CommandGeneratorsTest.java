package com.setupmyproject.models;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.setupmyproject.commands.DefaultAfterCommand;
import com.setupmyproject.commands.DefaultBeforeCommand;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.controllers.forms.CommandGenerator;

public class CommandGeneratorsTest {

	@Test
	public void shouldExecuteCommandsInOrder() {
		CommandGenerators commandGenerators = new CommandGenerators(
				Sets.newHashSet(new TestCommandGenerator(new SecondCommand()),
						new TestCommandGenerator(new FirstCommand())));

		List<ProjectCommand> commands = commandGenerators.getCommands();
		Assert.assertEquals(DefaultBeforeCommand.class, commands.get(0)
				.getClass());
		Assert.assertEquals(FirstCommand.class, commands.get(1)
				.getClass());
		Assert.assertEquals(SecondCommand.class, commands.get(2)
				.getClass());
		Assert.assertEquals(DefaultAfterCommand.class, commands.get(3)
				.getClass());
	}

	private static class TestCommandGenerator implements CommandGenerator {

		private ProjectCommand command;

		public TestCommandGenerator(ProjectCommand command) {
			super();
			this.command = command;
		}

		@Override
		public List<? extends ProjectCommand> createComand() {
			return Arrays.asList(command);
		}

	}

	private static class FirstCommand implements ProjectCommand {

		@Override
		public void execute(Project project,
				AddonRegistry addonRegistry, CommandGeneratorsQuery commandGenerators) {
		}

		@Override
		public List<ProjectCommand> before(CommandGenerators commandGenerators) {
			return Arrays.asList(commandGenerators
					.findProjectCommand(SecondCommand.class));
		}

		@Override
		public BigDecimal getPrice() {
			return BigDecimal.ZERO;
		}
		
		@Override
		public TimeToExecute getTimeToExecute() {
			return new TimeToExecute(0, TimeUnit.SECONDS);
		}

	}

	private static class SecondCommand implements ProjectCommand {

		@Override
		public void execute(Project project,
				AddonRegistry addonRegistry, CommandGeneratorsQuery commandGenerators) {
		}

		@Override
		public List<ProjectCommand> after(CommandGenerators commandGenerators) {
			return Arrays.asList(commandGenerators
					.findProjectCommand(FirstCommand.class));
		}
		
		@Override
		public BigDecimal getPrice() {
			return BigDecimal.ZERO;
		}	
		
		@Override
		public TimeToExecute getTimeToExecute() {
			return new TimeToExecute(0, TimeUnit.SECONDS);
		}
	}
}

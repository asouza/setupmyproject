package com.setupmyproject.models;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.ReadmeCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.commands.javaee.CDISetupCommand;
import com.setupmyproject.commands.javaee.JPASetupCommand;
import com.setupmyproject.commands.javaee.JSFBasicSetupCommand;
import com.setupmyproject.commands.javaee.PrimeFacesCommand;
import com.setupmyproject.components.ProjectCommandFormItem;

public enum JavaEEAddon implements Tooltipable, ProjectCommandFormItem {

	CDI {
		@Override
		public void execute(Project project, AddonRegistry addonRegistry,
				CommandGeneratorsQuery commandGenerators) {
			new CDISetupCommand().execute(project, addonRegistry,
					commandGenerators);
		}

		@Override
		public List<ProjectCommand> before(CommandGenerators commandGenerators) {
			return Arrays.asList(commandGenerators
					.findProjectCommand(JSFBasicSetupCommand.class),
					commandGenerators.findProjectCommand(ReadmeCommand.class));
		}

	},
	JPA {
		@Override
		public void execute(Project project, AddonRegistry addonRegistry,
				CommandGeneratorsQuery commandGenerators) {
			new JPASetupCommand().execute(project, addonRegistry,
					commandGenerators);
		}

	},
	PRIMEFACES {
		@Override
		public void execute(Project project, AddonRegistry addonRegistry,
				CommandGeneratorsQuery commandGenerators) {
			new PrimeFacesCommand().execute(project, addonRegistry,
					commandGenerators);
		}

		@Override
		public BigDecimal getPrice() {
			return BigDecimal.ZERO;
		}

		@Override
		public TimeToExecute getTimeToExecute() {
			return new TimeToExecute(5, TimeUnit.MINUTES);
		}

	};

	@Override
	public List<ProjectCommand> before(CommandGenerators commandGenerators) {
		return Arrays.asList(commandGenerators
				.findProjectCommand(ReadmeCommand.class));
	}

	@Override
	public BigDecimal getPrice() {
		return new BigDecimal("3.0");
	}

	@Override
	public String getNameKey() {
		return "option." + name();
	}

	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(15, TimeUnit.MINUTES);
	}
}

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
import com.setupmyproject.commands.springboot.SpringBootJPACommand;
import com.setupmyproject.commands.springboot.SpringBootJSPCommand;
import com.setupmyproject.commands.springboot.SpringBootSetupCommand;
import com.setupmyproject.components.ProjectCommandFormItem;

public enum SpringBootAddon implements Tooltipable, ProjectCommandFormItem {

	BOOT_SPRING_JPA {
		@Override
		public void execute(Project project, AddonRegistry addonRegistry,
				CommandGeneratorsQuery commandGenerators) {
			new SpringBootJPACommand().execute(project, addonRegistry,
					commandGenerators);
		}

		@Override
		public TimeToExecute getTimeToExecute() {
			return new TimeToExecute(2, TimeUnit.MINUTES);
		}

	},
	BOOT_SPRING_JSP {
		@Override
		public void execute(Project project, AddonRegistry addonRegistry,
				CommandGeneratorsQuery commandGenerators) {
			new SpringBootJSPCommand().execute(project, addonRegistry,
					commandGenerators);
		}

		@Override
		public BigDecimal getPrice() {
			return new BigDecimal("1.0");
		}

		@Override
		public TimeToExecute getTimeToExecute() {
			return new TimeToExecute(3, TimeUnit.MINUTES);
		}

	};

	@Override
	public List<ProjectCommand> before(CommandGenerators commandGenerators) {
		return Arrays.asList(commandGenerators
				.findProjectCommand(SpringBootSetupCommand.class),
				commandGenerators.findProjectCommand(ReadmeCommand.class));
	}

	@Override
	public BigDecimal getPrice() {
		return new BigDecimal("2.0");
	}

	@Override
	public String getNameKey() {
		return "option." + name();
	}

}
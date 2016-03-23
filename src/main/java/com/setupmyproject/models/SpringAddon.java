package com.setupmyproject.models;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.springframework.util.Assert;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.ReadmeCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.commands.spring.JSPViewResolverSetupCommand;
import com.setupmyproject.commands.spring.SpringJPASetupCommand;
import com.setupmyproject.commands.spring.SpringMVCBasicSetupCommand;
import com.setupmyproject.commands.spring.SpringSecurityBasicSetup;
import com.setupmyproject.components.ProjectCommandFormItem;

public enum SpringAddon implements Tooltipable,ProjectCommandFormItem{

	SPRING_SECURITY {
		@Override
		public void execute(Project project,AddonRegistry addonRegistry,CommandGeneratorsQuery commandGenerators) {
			new SpringSecurityBasicSetup().execute(project,addonRegistry,commandGenerators);
		}
		
		@Override
		public TimeToExecute getTimeToExecute() {
			return new TimeToExecute(30, TimeUnit.MINUTES);
		}
		
		
	},SPRING_JPA {
		@Override
		public void execute(Project project,AddonRegistry addonRegistry,CommandGeneratorsQuery commandGenerators) {
			new SpringJPASetupCommand().execute(project,addonRegistry,commandGenerators);
		}
		
		@Override
		public TimeToExecute getTimeToExecute() {
			return new TimeToExecute(15, TimeUnit.MINUTES);
		}
		
	},SPRING_JSP {
		@Override
		public void execute(Project project,AddonRegistry addonRegistry,CommandGeneratorsQuery commandGenerators) {
			new JSPViewResolverSetupCommand().execute(project, addonRegistry, commandGenerators);
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
		SpringMVCBasicSetupCommand springMVCBasicSetupCommand = commandGenerators.findProjectCommand(SpringMVCBasicSetupCommand.class);
		Assert.state(springMVCBasicSetupCommand!=null,"Para usar os Addons do Spring, tem que ter escolhido o comando "+SpringMVCBasicSetupCommand.class);
		
		ProjectCommand readmeCommand = commandGenerators.findProjectCommand(ReadmeCommand.class);
		Assert.state(readmeCommand!=null,"O ReadmeCommand deve ter sido adicionado, para que os addons do Spring funcionem corretamente");
		return Arrays.asList(springMVCBasicSetupCommand,readmeCommand);
	}
	
	@Override
	public BigDecimal getPrice() {
		return new BigDecimal("4.0");
	}
	
	@Override
	public String getNameKey() {
		return "option."+name();
	}
	
}

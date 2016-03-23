package com.setupmyproject.commands.deltaspike;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.CommandGeneratorsQuery;

public class DeltaSpikeSetupCommand implements ProjectCommand {
	
	private Logger logger = LoggerFactory.getLogger(DeltaSpikeSetupCommand.class);

	@Override
	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		
		logger.info("Gerando a configuracao basica do deltaSpike");

		ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);
		forgeHelper.getForceDependencyInstaller().forceInstallFromXmlFile(
				"/maven-dependencies/delta-spike/delta-spike-basic-deps.xml");
	}

	@Override
	public BigDecimal getPrice() {
		return BigDecimal.ZERO;
	}

	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(2, TimeUnit.MINUTES);
	}

}

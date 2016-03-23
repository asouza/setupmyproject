package com.setupmyproject.commands.deltaspike;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.commands.ReadmeCommand;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.CommandGeneratorsQuery;

public class DeltaSpikeJPACommandGenerator {
	
	private Logger logger = LoggerFactory.getLogger(DeltaSpikeJPACommandGenerator.class);

	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		
		logger.info("Gerando a configuracao da jpa para o deltaSpike");
		
		ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);
		installDeps(forgeHelper);
		generateEntityManagerProducer(forgeHelper, project, addonRegistry,
				commandGenerators);
		commandGenerators.findProjectCommand(ReadmeCommand.class).addSectionPath("/readmes/javaee-jpa-setup.md");
	}

	private void generateEntityManagerProducer(ForgeHelper forgeHelper,
			Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		MavenSetupForm mavenSetupForm = commandGenerators
				.find(MavenSetupForm.class);
		forgeHelper.getJavaSourceSaver().save(
				"/templates/delta-spike/EntityManagerProducer.jv",
				mavenSetupForm.packageFor("infra"));
	}

	private void installDeps(ForgeHelper forgeHelper) {
		forgeHelper.getForceDependencyInstaller().forceInstallFromXmlFile(
				"/maven-dependencies/delta-spike/delta-spike-jpa-deps.xml");
	}

}

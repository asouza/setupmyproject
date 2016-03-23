package com.setupmyproject.commands.springboot;

import java.util.AbstractMap;

import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.forge.JavaSourceSaver;
import com.setupmyproject.forge.ViewSaveConfiguration;
import com.setupmyproject.models.CommandGeneratorsQuery;

public class SpringBootJSPCommand {

	private static final String templateJSPIndex = "/templates/springmvc/pages/index.ftl";
	private static final String templateHomeController = "/templates/springboot/HomeController.jv";
	private Logger logger = LoggerFactory.getLogger(SpringBootJSPCommand.class);

	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {

		logger.info("Gerando configuracao do jsp para o spring boot");

		SpringBootSetupCommand bootSetupCommand = commandGenerators
				.findProjectCommand(SpringBootSetupCommand.class);
		bootSetupCommand.setHasJsp(true);
		addConfInApplicationProperties(bootSetupCommand);

		ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);
		addJSPDependencies(forgeHelper);
		addIndexPage(forgeHelper);
		addHomeController(forgeHelper, commandGenerators);
	}

	private void addHomeController(ForgeHelper forgeHelper, CommandGeneratorsQuery commandGenerators) {
		JavaSourceSaver javaSourceSaver = forgeHelper.getJavaSourceSaver();
		MavenSetupForm mavenSetupForm = commandGenerators.find(MavenSetupForm.class);
		javaSourceSaver.save(templateHomeController, mavenSetupForm.getProjectPackage()
				+ ".controllers");
	}

	private void addIndexPage(ForgeHelper forgeHelper) {
		forgeHelper.getViewCreator().create(templateJSPIndex,
				new ViewSaveConfiguration("WEB-INF/views/home/", "jsp"));
	}

	private void addJSPDependencies(ForgeHelper forgeHelper) {
		forgeHelper.getForceDependencyInstaller().forceInstall(
				DependencyBuilder.create().setGroupId("org.apache.tomcat.embed")
						.setArtifactId("tomcat-embed-jasper").setScopeType("provided"));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addConfInApplicationProperties(SpringBootSetupCommand bootSetupCommand) {
		bootSetupCommand.addApplicationPropertiesEntry(new AbstractMap.SimpleEntry(
				"spring.mvc.view.prefix", "/WEB-INF/views/"));
		bootSetupCommand.addApplicationPropertiesEntry(new AbstractMap.SimpleEntry(
				"spring.mvc.view.suffix", ".jsp"));
	}

}

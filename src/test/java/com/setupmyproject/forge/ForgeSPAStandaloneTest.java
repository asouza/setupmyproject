package com.setupmyproject.forge;

import com.google.common.collect.Sets;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.ReadmeCommand;
import com.setupmyproject.controllers.forms.*;
import com.setupmyproject.models.*;
import org.jboss.forge.addon.parser.java.projects.JavaWebProjectType;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.ProjectProvider;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.furnace.Furnace;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.forge.furnace.repositories.AddonRepositoryMode;
import org.jboss.forge.furnace.se.FurnaceFactory;
import org.jboss.forge.furnace.util.OperatingSystemUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

public class ForgeSPAStandaloneTest {
	public static void main(String[] args) throws Exception {
		Furnace furnace = FurnaceFactory.getInstance();
		File customPluginsPath = new File(
				OperatingSystemUtils.getUserForgeDir(), "addons");
		furnace.addRepository(AddonRepositoryMode.MUTABLE, customPluginsPath);

		Properties devProperties = new Properties();
		devProperties.load(ForgeSPAStandaloneTest.class
				.getResourceAsStream("/application.properties"));
		

		File defaultPluginsPath = new File(
				OperatingSystemUtils.getUserHomeDir(),
				devProperties.getProperty("forge.path"));
		furnace.addRepository(AddonRepositoryMode.IMMUTABLE, defaultPluginsPath);

		Future<Furnace> startAsync = furnace.startAsync();
		Furnace furnace2 = startAsync.get();

		String msg = createProject(furnace2.getAddonRegistry());
		System.out.println(msg);
		furnace2.stop();

	}

	private static String createProject(AddonRegistry addonRegistry) {
		ProjectFactory projectFactory = addonRegistry.getServices(
				ProjectFactory.class).get();
		ResourceFactory resourceFactory = addonRegistry.getServices(
				ResourceFactory.class).get();
		// Create a temporary directory as an example
		File underlyingResource = OperatingSystemUtils.createTempDir();

		Resource<File> projectDir = resourceFactory.create(underlyingResource);

		// This could return more than one provider, but since the maven addon
		// is the only one deployed, this is ok
		ProjectProvider projectProvider = addonRegistry.getServices(
				ProjectProvider.class).get();

		// Creating WAR project
		JavaWebProjectType javaWebProjectType = addonRegistry.getServices(
				JavaWebProjectType.class).get();
		Project project = projectFactory.createProject(projectDir,
				projectProvider, javaWebProjectType.getRequiredFacets());

		MavenSetupForm mavenSetupForm = new MavenSetupForm();
		mavenSetupForm.setBasePackage("com.mycompany.project.mydemoproject");

		SpaOptionForm spaOptionForm = new SpaOptionForm();
		spaOptionForm.setOption(SpaOption.REACTJS);


		ConfigurationForm configurationForm = new ConfigurationForm();
		configurationForm.setProjectType(ProjectType.SPA);

		BackendApiForm backendApiForm = new BackendApiForm();
		backendApiForm.setOption(BackendApi.JAX_RS);


		CommandGenerator readmeGenerator = new CommandGenerator() {

			@Override
			public List<? extends ProjectCommand> createComand() {
				return new ArrayList<ProjectCommand>(
						Arrays.asList(new ReadmeCommand()));
			}
		};

		CommandGenerators commandGenerators = new CommandGenerators(
				Sets.newHashSet(spaOptionForm, mavenSetupForm,
						backendApiForm, configurationForm,readmeGenerator));
		
		commandGenerators.executeAll(project, addonRegistry);

		System.out.println("Project Created in: " + project);

		return "projeto criado";
	}
}
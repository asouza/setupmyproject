package com.setupmyproject.forge;

import java.io.File;

import org.jboss.forge.addon.facets.FacetFactory;
import org.jboss.forge.addon.parser.java.projects.JavaWebProjectType;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.ProjectProvider;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.forge.furnace.util.OperatingSystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.async.DeferredResult;

import com.setupmyproject.conf.AccessEnvironment;
import com.setupmyproject.controllers.forms.ConfigurationForm;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.downloads.DownloadableProject;
import com.setupmyproject.models.CommandGenerators;
import com.setupmyproject.models.ProjectDefinition;
import com.setupmyproject.models.RequestedSetup;

public class ProjectCreator implements Runnable {

	private CommandGenerators commands;
	private DeferredResult<DownloadableProject> result;
	private Logger logger = LoggerFactory.getLogger(ProjectCreator.class);
	private RequestedSetup requestedSetup;
	private ForgeEngine projectCreatorEngine;

	public ProjectCreator(RequestedSetup requestedSetup,
			DeferredResult<DownloadableProject> result, ForgeEngine forgeEngine) {
		super();
		this.requestedSetup = requestedSetup;
		this.projectCreatorEngine = forgeEngine;
		this.commands = requestedSetup.getCommandGenerators();
		this.result = result;
	}

	@Override
	public void run() {
		try {
			String projectPath = createProject(projectCreatorEngine
					.getAddonRegistry());
			MavenSetupForm mavenForm = commands.find(MavenSetupForm.class);

			File zipFile = createFileToBeZippedBAndTokenFolder(mavenForm);
			result.setResult(new DownloadableProject(projectPath, zipFile));

		} catch (Exception exception) {
			result.setErrorResult(exception);
			throw new RuntimeException(exception);
		}
	}

	private File createFileToBeZippedBAndTokenFolder(MavenSetupForm mavenForm) {
		File zipDestinyFolder = new File("/tmp/"
				+ requestedSetup.getGeneratedToken());
		if (!zipDestinyFolder.exists()) {
			zipDestinyFolder.mkdir();
		}
		File zipFile = new File(zipDestinyFolder, mavenForm.getMavenInfo().getArtifactId()
				+ ".zip");
		return zipFile;
	}

	private String createProject(AddonRegistry addonRegistry) {
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

		FacetFactory facetFactory = addonRegistry.getServices(
				FacetFactory.class).get();
		addAllNecessaryFacets(project, facetFactory, requestedSetup);

		commands.executeAll(project, addonRegistry);

		logger.info("Project Created in: " + project);

		return project.getRoot().toString();
	}

	private void addAllNecessaryFacets(Project project,
			FacetFactory facetFactory, RequestedSetup requestedSetup) {
		ProjectDefinition projectDefinition = requestedSetup
				.getCommandGenerators().getProjectDefinition();
		projectDefinition.prepareFacets(facetFactory, project);

	}

}

package com.setupmyproject.forge;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.Future;

import org.jboss.forge.addon.facets.FacetFactory;
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

import com.google.common.collect.Sets;
import com.setupmyproject.controllers.forms.ConfigurationForm;
import com.setupmyproject.controllers.forms.DBConfigurationForm;
import com.setupmyproject.controllers.forms.JavaConfigurationForm;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.controllers.forms.SpringAddonForm;
import com.setupmyproject.controllers.forms.VRaptorAddonForm;
import com.setupmyproject.models.SpringAddon;
import com.setupmyproject.models.CommandGenerators;
import com.setupmyproject.models.DBType;
import com.setupmyproject.models.JavaVersion;
import com.setupmyproject.models.ProjectType;
import com.setupmyproject.models.VRaptorAddon;

public class VRaptorForgeStandaloneTest {
	public static void main(String[] args) throws Exception {
		Furnace furnace = FurnaceFactory.getInstance();
		File customPluginsPath = new File(
				OperatingSystemUtils.getUserForgeDir(), "addons");
		furnace.addRepository(AddonRepositoryMode.MUTABLE, customPluginsPath);

		Properties devProperties = new Properties();
		devProperties.load(VRaptorForgeStandaloneTest.class
				.getResourceAsStream("/dev.properties"));

		File defaultPluginsPath = new File(
				OperatingSystemUtils.getUserHomeDir(),
				devProperties.getProperty("forge.home"));
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
		FacetFactory facetFactory = addonRegistry.getServices(
				FacetFactory.class).get();
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

		DBConfigurationForm dbConfigurationForm = new DBConfigurationForm();
		dbConfigurationForm.setDbType(DBType.MYSQL5);

		ConfigurationForm configurationForm = new ConfigurationForm();
		configurationForm.setProjectType(ProjectType.VRAPTOR);
		configurationForm.getProjectType().prepareFacets(facetFactory, project);

		JavaConfigurationForm javaConfigurationForm = new JavaConfigurationForm();
		javaConfigurationForm.setVersion(JavaVersion.JAVA8);
		
		VRaptorAddonForm form = new VRaptorAddonForm();
		form.getAddons().add(VRaptorAddon.VRAPTOR_JAVA8);
		form.getAddons().add(VRaptorAddon.VRAPTOR_JPA);
		form.getAddons().add(VRaptorAddon.VRAPTOR_SIMPLEMAIL);

		CommandGenerators commandGenerators = new CommandGenerators(
				Sets.newHashSet(javaConfigurationForm, mavenSetupForm,
						dbConfigurationForm, configurationForm,form));
		
		commandGenerators.executeAll(project, addonRegistry);

		System.out.println("Project Created in: " + project);

		return "projeto criado";
	}
}
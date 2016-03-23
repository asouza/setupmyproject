package com.setupmyproject.forge;

import java.io.File;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.setupmyproject.controllers.forms.ConfigurationForm;
import com.setupmyproject.controllers.forms.DBConfigurationForm;
import com.setupmyproject.controllers.forms.JavaConfigurationForm;
import com.setupmyproject.controllers.forms.JavaEEAddonForm;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.models.CommandGenerators;
import com.setupmyproject.models.DBType;
import com.setupmyproject.models.JavaEEAddon;
import com.setupmyproject.models.JavaVersion;
import com.setupmyproject.models.ProjectType;

public class JSFForgeStandaloneTest {
	
	
	private static final Logger log = LoggerFactory.getLogger(JSFForgeStandaloneTest.class);
	
	public static void main(String[] args) throws Exception {
		
		Furnace furnace = FurnaceFactory.getInstance();
		Furnace furnace2 = null;
		try{
		File customPluginsPath = new File(OperatingSystemUtils.getUserForgeDir(), "addons");
		furnace.addRepository(AddonRepositoryMode.MUTABLE, customPluginsPath);

		java.util.Properties devProperties = new java.util.Properties();
		devProperties.load(JSFForgeStandaloneTest.class.getResourceAsStream("/dev.properties"));

		File defaultPluginsPath = new File(OperatingSystemUtils.getUserHomeDir(),
				devProperties.getProperty("forge.home"));
		furnace.addRepository(AddonRepositoryMode.IMMUTABLE, defaultPluginsPath);

		Future<Furnace> startAsync = furnace.startAsync();
		furnace2 = startAsync.get();

		String msg = createProject(furnace2.getAddonRegistry());
		System.out.println(msg);
		}finally{
			furnace2.stop();
		}

	}

	private static String createProject(AddonRegistry addonRegistry) {
		// Create a new project on a temporary directory.
		ProjectFactory projectFactory = addonRegistry.getServices(ProjectFactory.class).get();
		ResourceFactory resourceFactory = addonRegistry.getServices(ResourceFactory.class).get();
		File underlyingResource = OperatingSystemUtils.createTempDir();
		Resource<File> projectDir = resourceFactory.create(underlyingResource);

		// This could return more than one provider, but since the maven addon
		// is the only one deployed, this is ok
		ProjectProvider projectProvider = addonRegistry.getServices(ProjectProvider.class).get();

		// Creating WAR project
		JavaWebProjectType javaWebProjectType = addonRegistry.getServices(JavaWebProjectType.class).get();
		Project project = projectFactory.createProject(projectDir, projectProvider,
				javaWebProjectType.getRequiredFacets());
		FacetFactory facetFactory = addonRegistry.getServices(FacetFactory.class).get();

		// maven
		MavenSetupForm mavenSetupForm = new MavenSetupForm();
		mavenSetupForm.setBasePackage("br.com.bla.jsftestforge");

		// db
		DBConfigurationForm dbConfigurationForm = new DBConfigurationForm();
		dbConfigurationForm.setDbType(DBType.MYSQL5);

		// JSF
		ConfigurationForm configurationForm = new ConfigurationForm();
		configurationForm.setProjectType(ProjectType.JSF);

		// //cdi
		JavaEEAddonForm javaeeForm = new JavaEEAddonForm();
		javaeeForm.getAddons().add(JavaEEAddon.CDI);
		javaeeForm.getAddons().add(JavaEEAddon.JPA);
		javaeeForm.getAddons().add(JavaEEAddon.PRIMEFACES);

		JavaConfigurationForm javaConfigurationForm = new JavaConfigurationForm();
		javaConfigurationForm.setVersion(JavaVersion.JAVA8);

		CommandGenerators commandGenerators = new CommandGenerators(Sets.newHashSet(javaConfigurationForm,
				mavenSetupForm, dbConfigurationForm, configurationForm, javaeeForm));

		commandGenerators.executeAll(project, addonRegistry);

		System.out.println("Project Created in: " + project);

		return "projeto criado";
	}
}
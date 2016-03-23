package com.setupmyproject.commands.spring;

import java.util.HashMap;
import java.util.Map;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.commands.ReadmeCommand;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.controllers.forms.SpringAddonForm;
import com.setupmyproject.forge.ForceDependencyInstaller;
import com.setupmyproject.forge.JavaSourceSaver;
import com.setupmyproject.infra.SetupMyProjectXMLReader;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.SpringAddon;
import com.setupmyproject.models.CommandGenerators;
import com.setupmyproject.models.MavenDepencies;

public class SpringSecurityBasicSetup {

	private Logger logger = LoggerFactory
			.getLogger(SpringSecurityBasicSetup.class);

	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		logger.info("Começando a executar a configuração básica do spring security");

		ForceDependencyInstaller installer = new ForceDependencyInstaller(
				addonRegistry.getServices(DependencyInstaller.class).get(),
				project);
		MavenDepencies deps = SetupMyProjectXMLReader
				.read("/maven-dependencies/spring-security.xml",
						MavenDepencies.class);
		installer.forceInstall(deps);

		TemplateFactory templateFactory = addonRegistry.getServices(
				TemplateFactory.class).get();
		ResourceFactory resourceFactory = addonRegistry.getServices(
				ResourceFactory.class).get();
		JavaSourceSaver javaSourceSaver = new JavaSourceSaver(templateFactory,
				resourceFactory, project);

		HashMap<String, Object> params = new HashMap<String, Object>();

		createClass(
				"/templates/springmvc/SpringSecurityFilterConfiguration.jv",
				"conf", javaSourceSaver, commandGenerators, project, params);

		createClass("/templates/springmvc/SecurityConfiguration.jv", "conf",
				javaSourceSaver, commandGenerators, project, params);

		SpringAddonForm springForm = commandGenerators
				.find(SpringAddonForm.class);
		boolean jpaEnabled = springForm.getAddons().contains(SpringAddon.SPRING_JPA);
		params.put("jpaEnabled", jpaEnabled);
		createClass("/templates/springmvc/UserDetailsServiceDAO.jv", "daos",
				javaSourceSaver, commandGenerators, project, params);

		SpringMVCBasicSetupCommand springMVCommand = commandGenerators
				.findProjectCommand(SpringMVCBasicSetupCommand.class);
		springMVCommand.addClassToRootConfiguration("SecurityConfiguration");
		
		commandGenerators.findProjectCommand(ReadmeCommand.class).addSectionPath("/readmes/spring-security-setup.md");

	}

	private void createClass(String templatePath, String specificPackage,
			JavaSourceSaver javaSourceSaver,
			CommandGeneratorsQuery commandGenerators, Project project,
			Map<String, Object> params) {
		MavenSetupForm mavenSetupForm = commandGenerators
				.find(MavenSetupForm.class);
		javaSourceSaver.save(templatePath, mavenSetupForm.getProjectPackage()
				+ "." + specificPackage, params);

	}

}

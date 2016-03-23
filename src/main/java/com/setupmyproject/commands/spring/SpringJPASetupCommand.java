package com.setupmyproject.commands.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.commands.HibernateDependencies;
import com.setupmyproject.commands.ReadmeCommand;
import com.setupmyproject.controllers.forms.DBConfigurationForm;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForceDependencyInstaller;
import com.setupmyproject.forge.JavaSourceSaver;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.DatabaseInfo;

public class SpringJPASetupCommand {

	private Logger logger = LoggerFactory
			.getLogger(SpringJPASetupCommand.class);

	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		logger.info("Configurando a JPA para o Spring");

		ForceDependencyInstaller installer = new ForceDependencyInstaller(
				addonRegistry.getServices(DependencyInstaller.class).get(),
				project);
		installer.forceInstall(createDependencies());
		
		TemplateFactory templateFactory = addonRegistry.getServices(TemplateFactory.class).get();
		ResourceFactory resourceFactory = addonRegistry.getServices(ResourceFactory.class).get();		

		createJPAConfiguration(project, commandGenerators, templateFactory,
				resourceFactory);				
		
		SpringMVCBasicSetupCommand springMVCSetup = commandGenerators.findProjectCommand(SpringMVCBasicSetupCommand.class);
		springMVCSetup.addClassToRootConfiguration("JPAConfiguration");
		ReadmeCommand readmeCommand = commandGenerators.findProjectCommand(ReadmeCommand.class);
		readmeCommand.addSectionPath("/readmes/spring-jpa-setup.md");
		
		

	}

	private void createJPAConfiguration(Project project,
			CommandGeneratorsQuery commandGenerators,
			TemplateFactory templateFactory, ResourceFactory resourceFactory) {
		MavenSetupForm mavenSetupForm = commandGenerators.find(MavenSetupForm.class);
		DBConfigurationForm dbForm = commandGenerators.find(DBConfigurationForm.class);
		HashMap<Object, Object> params = new HashMap<>();
		params.put("projectPackage", mavenSetupForm.getProjectPackage());
		DatabaseInfo databaseInfo = new DatabaseInfo("", "");
		if(dbForm.getDbType() != null){
			databaseInfo = new DatabaseInfo(dbForm.getDbType().getDriverClassName(), dbForm.getDbType().url("DATABASE_NAME"));
		}
		params.put("dbInfo",databaseInfo);
		JavaSourceSaver javaSourceSaver = new JavaSourceSaver(templateFactory, resourceFactory,project);
		javaSourceSaver.save("/templates/springmvc/JPAConfiguration.jv", mavenSetupForm.getProjectPackage()+".conf",params);
	}

	private List<DependencyBuilder> createDependencies() {
		ArrayList<DependencyBuilder> deps = new ArrayList<>();
		
		deps.addAll(HibernateDependencies.getHibernateDependencies());
		deps.add(DependencyBuilder.create().setGroupId("org.springframework")
				.setArtifactId("spring-orm")
				.setVersion(SpringMVCBasicSetupCommand.spring_version));
		return deps;
	}

}

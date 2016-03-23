package com.setupmyproject.commands.springboot;

import java.util.AbstractMap;

import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.commands.ReadmeCommand;
import com.setupmyproject.controllers.forms.DBConfigurationForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.DatabaseInfo;

public class SpringBootJPACommand {

	private Logger logger = LoggerFactory.getLogger(SpringBootJPACommand.class);

	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {

		logger.info("Gerando configuracao da jpa para o Spring boot");
		ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);
		addDependency(forgeHelper);

		addDataSourceProperties(commandGenerators);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addDataSourceProperties(
			CommandGeneratorsQuery commandGenerators) {
		SpringBootSetupCommand bootCommand = commandGenerators
				.findProjectCommand(SpringBootSetupCommand.class);
		DBConfigurationForm dbForm = commandGenerators
				.find(DBConfigurationForm.class);
		DatabaseInfo databaseInfo = new DatabaseInfo("", "");
		if (dbForm.getDbType() != null) {
			databaseInfo = new DatabaseInfo(dbForm.getDbType()
					.getDriverClassName(), dbForm.getDbType().url(
					"DATABASE_NAME"));
		}
		bootCommand.addApplicationPropertiesEntry(new AbstractMap.SimpleEntry(
				"spring.datasource.url", databaseInfo.getDriverUrl()));
		bootCommand.addApplicationPropertiesEntry(new AbstractMap.SimpleEntry(
				"spring.datasource.username", "username"));
		bootCommand.addApplicationPropertiesEntry(new AbstractMap.SimpleEntry(
				"spring.datasource.password", "password"));
		bootCommand.addApplicationPropertiesEntry(new AbstractMap.SimpleEntry(
				"spring.datasource.driver-class-name", databaseInfo
						.getDriverClassName()));
		bootCommand.addApplicationPropertiesEntry(new AbstractMap.SimpleEntry(
				"spring.jpa.hibernate.ddl-auto", "update"));
		commandGenerators.findProjectCommand(ReadmeCommand.class).addSectionPath("/readmes/spring-boot-jpa-setup.md");

	}

	private void addDependency(ForgeHelper forgeHelper) {
		forgeHelper.getForceDependencyInstaller().forceInstall(DependencyBuilder.create().setGroupId(
				"org.springframework.boot").setArtifactId("spring-boot-starter-data-jpa"));
	}

}

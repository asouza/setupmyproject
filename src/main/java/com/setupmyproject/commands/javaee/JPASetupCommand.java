package com.setupmyproject.commands.javaee;

import java.util.Collection;

import org.jboss.forge.addon.javaee.jpa.JPAFacet_2_1;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.shrinkwrap.descriptor.api.persistence21.PersistenceDescriptor;
import org.jboss.shrinkwrap.descriptor.api.persistence21.PersistenceUnit;
import org.jboss.shrinkwrap.descriptor.api.persistence21.Properties;

import com.setupmyproject.commands.HibernateDependencies;
import com.setupmyproject.commands.ReadmeCommand;
import com.setupmyproject.commands.messages.JPAMessages;
import com.setupmyproject.controllers.forms.DBConfigurationForm;
import com.setupmyproject.forge.ForceDependencyInstaller;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.DBType;

public class JPASetupCommand {
	
	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {

		ForgeHelper forge = new ForgeHelper(project, addonRegistry);
		JPAFacet_2_1 jpaFacet = forge.installFacet(JPAFacet_2_1.class);
		PersistenceDescriptor persistenceXml = forge
				.getDescriptorFor(JPAFacet_2_1.class);

		PersistenceUnit<PersistenceDescriptor> persistenceUnit = persistenceXml
				.getOrCreatePersistenceUnit();
		persistenceUnit.name("default");		
		
		addClasses(persistenceUnit,commandGenerators);

		DBConfigurationForm dbConfiguration = commandGenerators
				.find(DBConfigurationForm.class);
		DBType dbType = dbConfiguration.getDbType();

		Properties<PersistenceUnit<PersistenceDescriptor>> props = persistenceUnit
				.getOrCreateProperties();
		props.createProperty().name("javax.persistence.jdbc.user")
				.value("your username here");
		props.createProperty().name("javax.persistence.jdbc.password")
				.value("your password here");
		props.createProperty().name("hibernate.show_sql").value("true");
		props.createProperty().name("hibernate.format_sql").value("true");
		props.createProperty().name("hibernate.hbm2ddl.auto").value("update");
		configureDatabaseSpecificProperties(dbType, props);

		jpaFacet.saveConfig(persistenceXml);

		ForceDependencyInstaller dependencyInstaller = forge
				.getForceDependencyInstaller();
		dependencyInstaller.forceInstall(HibernateDependencies
				.getHibernateDependencies(forge.getServerEnvironment(commandGenerators).getDependencyScope()));
		
		commandGenerators.findProjectCommand(ReadmeCommand.class).addSectionPath("/readmes/javaee-jpa-setup.md");

	}

	private void addClasses(
			PersistenceUnit<PersistenceDescriptor> persistenceUnit,
			CommandGeneratorsQuery commandGenerators) {
		Collection<Object> messages = commandGenerators.consumeMessage(JPAMessages.ADD_CLASS);
		messages.forEach(entityClass -> {
			persistenceUnit.clazz(entityClass.toString());
		});
	}

	private void configureDatabaseSpecificProperties(DBType dbType,
			Properties<PersistenceUnit<PersistenceDescriptor>> props) {
		String jdbcUrl = "";
		String driverClass = "";
		if (dbType != null) {
			jdbcUrl = dbType.url("DB_NAME");
			driverClass = dbType.getDriverClassName();
		}
		props.createProperty().name("javax.persistence.jdbc.url")
				.value(jdbcUrl);
		props.createProperty().name("javax.persistence.jdbc.driver")
				.value(driverClass);
	}

}

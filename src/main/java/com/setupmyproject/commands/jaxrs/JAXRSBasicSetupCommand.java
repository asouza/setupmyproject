package com.setupmyproject.commands.jaxrs;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.commands.messages.ServerEnvironmentMessage;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.controllers.forms.ServerEnvironmentAddonForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.CommandGenerators;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.ServerEnvironmentAddon;

public class JAXRSBasicSetupCommand implements ProjectCommand {

	private static Logger logger = LoggerFactory
			.getLogger(JAXRSBasicSetupCommand.class);

	@Override
	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		logger.info("gerando a configuracao do jaxrs");
		ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);
		MavenSetupForm mavenForm = commandGenerators.find(MavenSetupForm.class);
		HashMap<Object, Object> templateParams = new HashMap<>();
		templateParams.put("mavenForm", mavenForm);
		
		
		ServerEnvironmentAddon serverType = forgeHelper.getServerEnvironment(commandGenerators);
		
		if(serverType.equals(ServerEnvironmentAddon.SERVLET_CONTAINER_3_X)){
			instalDependencies(forgeHelper);
		}
		
		createProductExampleModel(forgeHelper, mavenForm);
		createResourceConf(forgeHelper,mavenForm);
		createResourceClass(forgeHelper, mavenForm, templateParams);
		createClientsClasses(forgeHelper, mavenForm, templateParams);
		
	}

	private void createResourceConf(ForgeHelper forgeHelper,MavenSetupForm mavenForm) {
		forgeHelper.getJavaSourceSaver().save("/templates/jaxrs/ApplicationConf.jv", mavenForm.packageFor("resources"));				
		forgeHelper.getJavaSourceSaver().save("/templates/jaxrs/CorsFilter.jv", mavenForm.packageFor("resources"));
	}

	private void createClientsClasses(ForgeHelper forgeHelper,
			MavenSetupForm mavenForm, HashMap<Object, Object> templateParams) {
		forgeHelper.getJavaSourceSaver().saveTest("/templates/jaxrs/ProductCreationClientTest.jv", mavenForm.packageFor("resources"),templateParams);
		forgeHelper.getJavaSourceSaver().saveTest("/templates/jaxrs/ProductListClientTest.jv", mavenForm.packageFor("resources"),templateParams);
	}

	private void createResourceClass(ForgeHelper forgeHelper,
			MavenSetupForm mavenForm, HashMap<Object, Object> templateParams) {
		forgeHelper.getJavaSourceSaver().save("/templates/jaxrs/ProductResource.jv", mavenForm.packageFor("resources"),templateParams);
	}

	private void createProductExampleModel(ForgeHelper forgeHelper,
			MavenSetupForm mavenForm) {
		forgeHelper.getJavaSourceSaver().save("/templates/jaxrs/Product.jv", mavenForm.packageFor("models"));
	}

	private void instalDependencies(ForgeHelper forgeHelper) {
		forgeHelper.getForceDependencyInstaller().forceInstallFromXmlFile("/maven-dependencies/jaxrs-resteasy.xml");
	}

	@Override
	public BigDecimal getPrice() {
		return new BigDecimal("3.0");
	}

	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(15, TimeUnit.MINUTES);
	}

}

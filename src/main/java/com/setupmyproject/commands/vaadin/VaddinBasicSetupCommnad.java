package com.setupmyproject.commands.vaadin;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.jboss.forge.addon.maven.projects.facets.MavenDependencyFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.forge.JavaSourceSaver;
import com.setupmyproject.models.CommandGeneratorsQuery;

public class VaddinBasicSetupCommnad implements ProjectCommand {

	private static String templateAddressBookUI = "/templates/vaadin/AddressbookUI.jv";
	private static String templateContact = "/templates/vaadin/Contact.jv";
	private static String templateContactDAO = "/templates/vaadin/ContactDAO.jv";
	private static String templateContactForm = "/templates/vaadin/ContactForm.jv";

	@Override
	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);
		forgeHelper.getForceDependencyInstaller().forceInstallFromXmlFile(
				"/maven-dependencies/vadim-basic-deps.xml");

		generateExampleClasses(commandGenerators, forgeHelper);
		configureVaadinRepo(project);
	}

	private void configureVaadinRepo(Project project) {
		MavenDependencyFacet facet = project
				.getFacet(MavenDependencyFacet.class);
		facet.addRepository("vaadin-addons",
				"http://maven.vaadin.com/vaadin-addons");

	}

	private void generateExampleClasses(
			CommandGeneratorsQuery commandGenerators, ForgeHelper forgeHelper) {
		JavaSourceSaver javaSourceSaver = forgeHelper.getJavaSourceSaver();
		MavenSetupForm mavenSetupForm = commandGenerators
				.find(MavenSetupForm.class);
		
		String modelsPackage = mavenSetupForm.packageFor("models");
		javaSourceSaver.save(templateContact, modelsPackage);
		
		Function<JavaClassSource, JavaClassSource> importModelsFunction =  (javaSource) -> {
			javaSource.addImport(modelsPackage+".Contact");
			return javaSource;
		};
		
		String daosPackage = mavenSetupForm.packageFor("daos");
		
		Function<JavaClassSource, JavaClassSource> importDaoAndModlesOFunction =  (javaSource) -> {
			javaSource.addImport(daosPackage+".ContactDAO");
			importModelsFunction.apply(javaSource);
			return javaSource;
		};		
				
		String uiPackage = mavenSetupForm.packageFor("ui");
		javaSourceSaver.save(templateAddressBookUI, uiPackage,importDaoAndModlesOFunction);

		javaSourceSaver.save(templateContactDAO, daosPackage,importModelsFunction); 		
		
		javaSourceSaver.save(templateContactForm, uiPackage,importModelsFunction);
	}

	@Override
	public BigDecimal getPrice() {
		return BigDecimal.ZERO;
	}

	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(30, TimeUnit.MINUTES);
	}
}

package com.setupmyproject.commands.javaee;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.jboss.forge.addon.javaee.cdi.CDIFacet_1_1;
import org.jboss.forge.addon.javaee.faces.FacesFacet_2_2;
import org.jboss.forge.addon.javaee.servlet.ServletFacet_3_1;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.ReadmeCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForceDependencyInstaller;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.forge.JavaSourceSaver;
import com.setupmyproject.forge.ViewCreator;
import com.setupmyproject.forge.ViewSaveConfiguration;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.ServerEnvironmentAddon;

public class JSFBasicSetupCommand implements ProjectCommand {

	private Logger logger = LoggerFactory.getLogger(JSFBasicSetupCommand.class);
	private static final String templateHomeController = "/templates/jsf/basic/HelloWorldController.jv";
	private static final String templateXHTMLIndex = "/templates/jsf/pages/index.ftl";

	/**
	 * Annotations usadas quando o ManagedBean é do JSF
	 */
	private Function<JavaClassSource, JavaClassSource> addJSFAnnotations = (source) -> {
		source.addAnnotation("javax.faces.bean.ManagedBean");
		source.addAnnotation("javax.faces.bean.RequestScoped");
		return source;
	};

	/**
	 * Annotations usadas quando o ManagedBean é do CDI
	 */
	private Function<JavaClassSource, JavaClassSource> addCDIAnnotations = (source) -> {
		source.addAnnotation("javax.enterprise.inject.Model");
		return source;
	};

	@Override
	public void execute(Project project, AddonRegistry addonRegistry, CommandGeneratorsQuery commandGenerators) {
		logger.info("Executando o comando de configuração do JSF");

		ForgeHelper forge = new ForgeHelper(project, addonRegistry);
		forge.installFacetAndDescriptor(ServletFacet_3_1.class);
		FacesFacet_2_2 facesFacet = forge.installFacet(FacesFacet_2_2.class);
		facesFacet.setFacesMapping("*.xhtml");

		ServerEnvironmentAddon serverEnvironment = forge.getServerEnvironment(commandGenerators);
		
		ForceDependencyInstaller dependencyInstaller = forge.getForceDependencyInstaller();
		dependencyInstaller.forceInstall("javax.faces", "javax.faces-api", "2.2",serverEnvironment.getDependencyScope());
		dependencyInstaller.forceInstall("com.sun.faces", "jsf-impl", "2.2.10",serverEnvironment.getDependencyScope());

		generateHelloWorld(forge, project, addonRegistry, commandGenerators);
		
		ReadmeCommand readmeCommand = commandGenerators.findProjectCommand(ReadmeCommand.class);
		readmeCommand.addSectionPath("/readmes/jsf-setup.md");

	}

	private void generateHelloWorld(ForgeHelper forge, Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		MavenSetupForm mavenSetupForm = commandGenerators.find(MavenSetupForm.class);
		String managedBeansPackage = mavenSetupForm.getProjectPackage() + ".controllers";
		JavaSourceSaver javaSourceSaver = forge.getJavaSourceSaver();

		if (forge.hasFacet(CDIFacet_1_1.class)) {
			javaSourceSaver.save(templateHomeController, managedBeansPackage, addCDIAnnotations);
		} else {
			javaSourceSaver.save(templateHomeController, managedBeansPackage, addJSFAnnotations);
		}

		ViewCreator viewCreator = forge.getViewCreator();
		viewCreator.create(templateXHTMLIndex, new ViewSaveConfiguration("", "xhtml"));
	}

	@Override
	public BigDecimal getPrice() {
		return new BigDecimal("5");
	}
	
	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(25, TimeUnit.MINUTES);
	}
}

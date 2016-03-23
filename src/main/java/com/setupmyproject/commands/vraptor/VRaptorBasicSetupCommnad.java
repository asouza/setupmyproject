package com.setupmyproject.commands.vraptor;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.javaee.validation.ValidationFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.shrinkwrap.descriptor.api.validationConfiguration11.ExecutableValidationType;
import org.jboss.shrinkwrap.descriptor.api.validationConfiguration11.ValidationConfigurationDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.ReadmeCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.commands.javaee.CDISetupCommand;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.forge.JavaSourceSaver;
import com.setupmyproject.forge.ViewSaveConfiguration;
import com.setupmyproject.infra.SetupMyProjectXMLReader;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.MavenDepencies;
import com.setupmyproject.models.ServerEnvironmentAddon;

public class VRaptorBasicSetupCommnad implements ProjectCommand {

	private Logger logger = LoggerFactory
			.getLogger(VRaptorBasicSetupCommnad.class);
	private static final String templateHomeController = "/templates/vraptor/Controller.jv";
	private static final String templateJSPIndex = "/templates/vraptor/pages/index.ftl";

	// aqui tem que gerar um controller do vraptor e uma view de hello world
	// caso o plugin da jpa esteja adicionado, adicionamos um @Transactional em
	// cima do método

	@Override
	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		logger.info("fazendo configuracao basica do vraptor");
		ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);
		new CDISetupCommand().execute(project, addonRegistry, commandGenerators);
		installValidation(project);
		configureDependencies(forgeHelper, commandGenerators);
		createHelloWorldControllerAndPage(commandGenerators, forgeHelper);
		commandGenerators.findProjectCommand(ReadmeCommand.class).addSectionPath("/readmes/vraptor-setup.md");
	}

	private void createHelloWorldControllerAndPage(
			CommandGeneratorsQuery commandGenerators, ForgeHelper forgeHelper) {
		forgeHelper.getViewCreator().create(templateJSPIndex,
				new ViewSaveConfiguration("WEB-INF/jsp/home/", "jsp"));
		JavaSourceSaver javaSourceSaver = forgeHelper.getJavaSourceSaver();
		MavenSetupForm mavenForm = commandGenerators.find(MavenSetupForm.class);
		javaSourceSaver.save(templateHomeController,
				mavenForm.getProjectPackage() + ".controllers");
	}

	private void configureDependencies(ForgeHelper forgeHelper,
			CommandGeneratorsQuery generatorsQuery) {
		ServerEnvironmentAddon serverEnvironment = forgeHelper
				.getServerEnvironment(generatorsQuery);
		MavenDepencies deps;

		// TODO tinha um jeito melhor. Eu vou ter que verificar ambientes em
		// cenários diferentes, teriam que ser várias abstrações.
		switch (serverEnvironment) {
			case SERVER_JAVA_EE_7: {
				deps = SetupMyProjectXMLReader.read(
						"/maven-dependencies/vraptor-basic-deps-ee.xml",
						MavenDepencies.class);
				break;	
			}
			case SERVLET_CONTAINER_3_X: {
				deps = SetupMyProjectXMLReader.read(
						"/maven-dependencies/vraptor-basic-deps.xml",
						MavenDepencies.class);
				break;
	
			}
			default: {
				deps = SetupMyProjectXMLReader.read(
						"/maven-dependencies/vraptor-basic-deps.xml",
						MavenDepencies.class);
				break;				
			}
		}

		forgeHelper.getForceDependencyInstaller().forceInstall(deps);
	}

	private void installValidation(Project project) {
		ValidationFacet facet = project.getFacet(ValidationFacet.class);
		facet.install();
		ValidationConfigurationDescriptor config = facet.getConfig().version(
				"1.1");
		ExecutableValidationType<ValidationConfigurationDescriptor> executableValidation = config
				.getOrCreateExecutableValidation();
		executableValidation.enabled(false);
		facet.saveConfig(config);
	}

	@Override
	public BigDecimal getPrice() {
		return BigDecimal.ZERO;
	}

	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(45, TimeUnit.MINUTES);
	}

}

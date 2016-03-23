package com.setupmyproject.commands.spring;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.jboss.forge.addon.dependencies.builder.CoordinateBuilder;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.controllers.forms.ServerEnvironmentAddonForm;
import com.setupmyproject.forge.ForceDependencyInstaller;
import com.setupmyproject.forge.JavaSourceSaver;
import com.setupmyproject.models.CommandGenerators;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.ServerEnvironmentAddon;

public class SpringMVCBasicSetupCommand implements ProjectCommand {

	private static final String templateHomeController = "/templates/springmvc/HomeController.jv";
	private static final String templateAppWebConfiguration = "/templates/springmvc/AppWebConfiguration.jv";
	private static final String templateSpringMVCServlet = "/templates/springmvc/SpringMVCServlet.jv";
	public final static String spring_version = "4.1.0.RELEASE";
	private Logger logger = LoggerFactory
			.getLogger(SpringMVCBasicSetupCommand.class);
	private Set<String> appWebConfigurationImports = new HashSet<>();
	private Set<String> appWebConfigurationBeanMethods = new HashSet<>();
	private Set<String> rootConfigurationClasses = Sets
			.newHashSet("AppWebConfiguration.class");

	@Override
	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		ForceDependencyInstaller installer = new ForceDependencyInstaller(
				addonRegistry.getServices(DependencyInstaller.class).get(),
				project);

		logger.info("Gerando a configuracao final do Spring MVC");

		createDependencies().stream().forEach(installer::forceInstall);
		installer.forceInstallFromXmlFile("/maven-dependencies/servlet-container-server.xml");

		TemplateFactory templateFactory = addonRegistry.getServices(
				TemplateFactory.class).get();
		ResourceFactory resourceFactory = addonRegistry.getServices(
				ResourceFactory.class).get();

		JavaSourceSaver javaSourceSaver = new JavaSourceSaver(templateFactory,
				resourceFactory, project);

		MavenSetupForm mavenSetupForm = commandGenerators
				.find(MavenSetupForm.class);

		HashMap<Object, Object> params = new HashMap<>();
		params.put("package", mavenSetupForm.getProjectPackage());
		params.put("imports", appWebConfigurationImports);
		params.put("beanMethods", appWebConfigurationBeanMethods);
		params.put("rootConfigurationClasses", rootConfigurationClasses
				.stream().collect(Collectors.joining(",")));
		javaSourceSaver.save(templateSpringMVCServlet,
				mavenSetupForm.getProjectPackage() + ".conf", params);

		javaSourceSaver.save(templateAppWebConfiguration,
				mavenSetupForm.getProjectPackage() + ".conf", params);

		javaSourceSaver.save(templateHomeController,
				mavenSetupForm.getProjectPackage() + ".controllers");

	}

	private Collection<DependencyBuilder> createDependencies() {
		Collection<DependencyBuilder> deps = new ArrayList<DependencyBuilder>();
		deps.add(DependencyBuilder.create().setGroupId("org.springframework")
				.setArtifactId("spring-webmvc").setVersion(spring_version)
				.setScopeType("compile"));

		CoordinateBuilder excludeOldServletApi = CoordinateBuilder.create()
				.setGroupId("javax.servlet").setArtifactId("servlet-api");

		deps.add(DependencyBuilder.create()
				.setGroupId("javax.servlet.jsp.jstl").setArtifactId("jstl-api")
				.setVersion("1.2").setScopeType("compile")
				.setExcludedCoordinates(Arrays.asList(excludeOldServletApi)));

		deps.add(DependencyBuilder.create().setGroupId("org.glassfish.web")
				.setArtifactId("jstl-impl").setVersion("1.2")
				.setScopeType("compile")
				.setExcludedCoordinates(Arrays.asList(excludeOldServletApi)));

		deps.add(DependencyBuilder.create().setGroupId("org.slf4j")
				.setArtifactId("slf4j-api").setVersion("1.6.1")
				.setScopeType("compile"));

		deps.add(DependencyBuilder.create().setGroupId("org.slf4j")
				.setArtifactId("jcl-over-slf4j").setVersion("1.6.1")
				.setScopeType("runtime"));

		deps.add(DependencyBuilder.create().setGroupId("org.slf4j")
				.setArtifactId("slf4j-log4j12").setVersion("1.6.1")
				.setScopeType("runtime"));

		deps.add(DependencyBuilder.create().setGroupId("log4j")
				.setArtifactId("log4j").setVersion("1.2.16")
				.setScopeType("runtime"));
		return deps;
	}

	/**
	 * 
	 * @param importLine
	 *            classe que deve ser importada na AppWebConfiguration. Ex:
	 *            org.springframework.context.annotation.Configuration
	 */
	public void addImportAppWebConfiguration(String importLine) {
		appWebConfigurationImports.add(importLine);
	}

	/**
	 * 
	 * @param beanMethod
	 *            método que deve ser adicionada ao AppWebConfiguration. Dê uma
	 *            olhada no beanMethod in {@link JSPViewResolverSetupCommand}
	 */
	public void addMethodAppWebConfiguration(String beanMethod) {
		appWebConfigurationBeanMethods.add(beanMethod);
	}

	/**
	 * 
	 * @param klassName
	 *            nome da classe que vai ser adicionada no método
	 *            getRootConfigClasses na configuração do Servlet. Ex:
	 *            AppWebConfiguration
	 */
	public void addClassToRootConfiguration(String klassName) {
		rootConfigurationClasses.add(klassName + ".class");
	}

	@Override
	public BigDecimal getPrice() {
		return new BigDecimal("5.00");
	}

	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(30, TimeUnit.MINUTES);
	}

}

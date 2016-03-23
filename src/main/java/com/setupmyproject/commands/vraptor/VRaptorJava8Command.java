package com.setupmyproject.commands.vraptor;

import org.jboss.forge.addon.dependencies.builder.CoordinateBuilder;
import org.jboss.forge.addon.maven.plugins.ConfigurationBuilder;
import org.jboss.forge.addon.maven.plugins.MavenPluginBuilder;
import org.jboss.forge.addon.maven.projects.MavenPluginFacet;
import org.jboss.forge.addon.parser.java.facets.JavaCompilerFacet.CompilerVersion;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.forge.ForceDependencyInstaller;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.CommandGeneratorsQuery;

public class VRaptorJava8Command {

	private Logger logger = LoggerFactory.getLogger(VRaptorJava8Command.class);

	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {

		logger.info("configurando o java8 no projeto do vraptor");

		ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);
		installPluginDependencies(forgeHelper);
		enableParametersInformationInCompilation(project);
	}

	private void enableParametersInformationInCompilation(Project project) {
		ConfigurationBuilder builder = ConfigurationBuilder.create();
		builder.createConfigurationElement("source").setText(
				CompilerVersion.JAVA_1_8.toString());
		builder.createConfigurationElement("target").setText(
				CompilerVersion.JAVA_1_8.toString());
		builder.createConfigurationElement("compilerArguments")
				.createConfigurationElement("parameters");
		
		MavenPluginFacet pluginFacet = project.getFacet(MavenPluginFacet.class);
		MavenPluginBuilder plugin = MavenPluginBuilder.create();
		plugin.setCoordinate(CoordinateBuilder.create("org.apache.maven.plugins:maven-compiler-plugin:3.1"));
		plugin.setConfiguration(builder);
		pluginFacet.addPlugin(plugin);

		
	}

	private void installPluginDependencies(ForgeHelper forgeHelper) {
		ForceDependencyInstaller installer = forgeHelper
				.getForceDependencyInstaller();
		installer
				.forceInstallFromXmlFile("/maven-dependencies/vraptor-java8-deps.xml");
	}

}

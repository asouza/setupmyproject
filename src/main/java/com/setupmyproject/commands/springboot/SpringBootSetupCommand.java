package com.setupmyproject.commands.springboot;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.jboss.forge.addon.dependencies.builder.CoordinateBuilder;
import org.jboss.forge.addon.maven.plugins.MavenPluginBuilder;
import org.jboss.forge.addon.maven.projects.MavenFacet;
import org.jboss.forge.addon.maven.projects.MavenPluginFacet;
import org.jboss.forge.addon.maven.projects.facets.MavenDependencyFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.ResourcesFacet;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.forge.JavaSourceSaver;
import com.setupmyproject.infra.SetupMyProjectXMLReader;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.MavenDepencies;

public class SpringBootSetupCommand implements ProjectCommand {

	public static final String BOOT_VERSION = "1.5.3.RELEASE";
	private Logger logger = LoggerFactory
			.getLogger(SpringBootSetupCommand.class);
	private boolean hasJsp = false;
	private List<Entry<String,String>> applicationPropertiesValues = new ArrayList<Entry<String,String>>();

	private static final String templateBoot = "/templates/springboot/Boot.jv";

	public void setHasJsp(boolean hasJsp) {
		this.hasJsp = hasJsp;
	}
	
	public void addApplicationPropertiesEntry(Entry<String, String> entry) {
		applicationPropertiesValues.add(entry);
	}

	@Override
	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		logger.info("Começando configuracao do spring boot");
		ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);
		configureDependencies(forgeHelper);
		configurePlugins(forgeHelper);
		configureParent(forgeHelper);
		createBootClass(forgeHelper, commandGenerators);
		try {
			createApplicationProperties(forgeHelper);
		} catch (IOException e) {
			logger.error("Não foi possível criar o application.properties {}",e);
		}
	}

	private void createApplicationProperties(ForgeHelper forgeHelper) throws IOException {
		//TODO isolar esse codigo
		ResourcesFacet resources = forgeHelper.getProject().getFacet(ResourcesFacet.class);
		FileResource<?> resource = (FileResource<?>) resources
				.getResourceDirectory().getChild("application.properties");
		
		if(!resource.exists()){
			resource.createNewFile();			
		}		
		
		Properties properties = new Properties();
		properties.load(resource.getResourceInputStream());
		for (Entry<String, String> entry : applicationPropertiesValues) {
			properties.put(entry.getKey(), entry.getValue());
		}
		properties.store(resource.getResourceOutputStream(),"spring boot properties");		
	}

	private void createBootClass(ForgeHelper forgeHelper, CommandGeneratorsQuery commandGenerators) {
		JavaSourceSaver javaSourceSaver = forgeHelper.getJavaSourceSaver();
		MavenSetupForm mavenForm = commandGenerators.find(MavenSetupForm.class);	
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		ArrayList<String> imports = new ArrayList<String>();
		//FIXME fiz esse código porque o addImport pelo addImport não tava rolando
		if(!hasJsp){
			imports.add("org.springframework.web.bind.annotation.GetMapping");
			imports.add("org.springframework.web.bind.annotation.ResponseBody");
			imports.add("org.springframework.stereotype.Controller");
		}
		params.put("imports", imports);
		javaSourceSaver.save(templateBoot, mavenForm.getBasePackage(),params,(javaSource) -> {
			if(!hasJsp){
				addIndexResponseBodyIndexMethod(javaSource);
			}
			return javaSource;
		});
	}

	private void addIndexResponseBodyIndexMethod(JavaClassSource javaSource) {
		javaSource.addAnnotation("org.springframework.stereotype.Controller");
		String indexMethod = new StringBuilder().append("@GetMapping(\"/\")\n")
				.append("@ResponseBody\n")
				.append("public String home() {")
				.append("return \"home\";\n")
				.append("}")
				.toString();
		javaSource.addMethod(indexMethod);
	}

	private void configureParent(ForgeHelper forgeHelper) {
		MavenFacet maven = forgeHelper.getProject().getFacet(MavenFacet.class);
		Model pom = maven.getModel();
		Parent parent = new Parent();
		parent.setGroupId("org.springframework.boot");
		parent.setArtifactId("spring-boot-starter-parent");
		parent.setVersion(BOOT_VERSION);
		pom.setParent(parent);
		pom.setPackaging("jar");
		maven.setModel(pom);
	}


	private void configurePlugins(ForgeHelper forgeHelper) {
		MavenPluginFacet facet = forgeHelper.getProject().getFacet(
				MavenPluginFacet.class);
		MavenPluginBuilder plugin = MavenPluginBuilder.create();
		plugin.setCoordinate(CoordinateBuilder
				.create("org.springframework.boot:spring-boot-maven-plugin"));
		facet.addPlugin(plugin);
	}

	private void configureDependencies(ForgeHelper forgeHelper) {
		MavenDepencies deps = SetupMyProjectXMLReader.read(
				"/maven-dependencies/springboot/boot-deps.xml",
				MavenDepencies.class);
		forgeHelper.getForceDependencyInstaller().forceInstall(deps);		
	}

	@Override
	public BigDecimal getPrice() {
		return new BigDecimal("5.00");
	}

	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(7, TimeUnit.MINUTES);
	}

}

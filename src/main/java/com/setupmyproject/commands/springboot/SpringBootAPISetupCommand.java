package com.setupmyproject.commands.springboot;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.ServerEnvironmentAddon;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.jboss.forge.addon.maven.projects.MavenFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.ResourcesFacet;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SpringBootAPISetupCommand implements ProjectCommand {

	public static final String BOOT_VERSION = "1.5.3.RELEASE";
	private List<Map.Entry<String,String>> applicationPropertiesValues = new ArrayList<Map.Entry<String,String>>();

	private static Logger logger = LoggerFactory
			.getLogger(SpringBootAPISetupCommand.class);

	@Override
	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		logger.info("gerando a configuracao do spring-boot-api");
		ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);
		MavenSetupForm mavenForm = commandGenerators.find(MavenSetupForm.class);
		HashMap<Object, Object> templateParams = new HashMap<>();
		templateParams.put("mavenForm", mavenForm);
		
		
		instalDependencies(forgeHelper);
		configureParent(forgeHelper);
		createProductExampleModel(forgeHelper, mavenForm);
		createResourceConf(forgeHelper,mavenForm);
		createResourceClass(forgeHelper, mavenForm, templateParams);

		try {
			createApplicationProperties(forgeHelper);
		} catch (IOException e) {
			logger.error("Não foi possível criar o application.properties {}",e);
		}

	}

	//FIXME: duplicate from SpringBootSetupCommand
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
		for (Map.Entry<String, String> entry : applicationPropertiesValues) {
			properties.put(entry.getKey(), entry.getValue());
		}
		properties.store(resource.getResourceOutputStream(),"spring boot properties");
	}

	//FIXME: duplicate from SpringBootSetupCommand
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

	private void createResourceConf(ForgeHelper forgeHelper,MavenSetupForm mavenForm) {
		forgeHelper.getJavaSourceSaver().save("/templates/spring-boot-api/Boot.jv", mavenForm.getBasePackage());
	}


	private void createResourceClass(ForgeHelper forgeHelper,
			MavenSetupForm mavenForm, HashMap<Object, Object> templateParams) {
		forgeHelper.getJavaSourceSaver().save("/templates/spring-boot-api/ProductController.jv", mavenForm.packageFor("controllers"),templateParams);
	}

	private void createProductExampleModel(ForgeHelper forgeHelper,
			MavenSetupForm mavenForm) {
		forgeHelper.getJavaSourceSaver().save("/templates/spring-boot-api/Product.jv", mavenForm.packageFor("models"));
	}

	private void instalDependencies(ForgeHelper forgeHelper) {
		forgeHelper.getForceDependencyInstaller().forceInstallFromXmlFile("/maven-dependencies/spring-boot-api.xml");
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

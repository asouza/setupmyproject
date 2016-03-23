package com.setupmyproject.commands.vraptor;

import java.io.IOException;
import java.util.Properties;

import org.jboss.forge.addon.javaee.cdi.CDIFacet_1_1;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.ResourcesFacet;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.shrinkwrap.descriptor.api.beans11.BeansDescriptor;
import org.jboss.shrinkwrap.descriptor.api.beans11.Scan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.commands.ReadmeCommand;
import com.setupmyproject.forge.ForceDependencyInstaller;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.CommandGeneratorsQuery;

public class VRaptorSimpleMailCommand {
	
	private Logger logger = LoggerFactory.getLogger(VRaptorSimpleMailCommand.class);

	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		logger.info("Configurando o plugin de email para o vraptor");
		ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);
		installPluginDependencies(forgeHelper);
		try {
			writeMailConfInDevEnvironmentProperties(project);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		excludeClassesFromBeansXml(project);
		commandGenerators.findProjectCommand(ReadmeCommand.class).addSectionPath("/readmes/vraptor-mailer-plugin.md");
		
		
	}
	
	/**
	 * Não sei bem pq tem que excluir essa classe, mas isso estava no plugin e eu copiei :).
	 * @param project
	 */
	private void excludeClassesFromBeansXml(Project project) {
		CDIFacet_1_1 facet = project.getFacet(CDIFacet_1_1.class);
		BeansDescriptor config = facet.getConfig();
		Scan<BeansDescriptor> scan = config.getOrCreateScan();
		scan.getOrCreateExclude().name("com.google.common.util.concurrent.MoreExecutors$SameThreadExecutorService");
		facet.saveConfig(config);
	}	
	
	private void installPluginDependencies(ForgeHelper forgeHelper) {
		ForceDependencyInstaller installer = forgeHelper
				.getForceDependencyInstaller();
		installer
				.forceInstallFromXmlFile("/maven-dependencies/vraptor-simplemail-deps.xml");
	}	
	
	private void writeMailConfInDevEnvironmentProperties(Project project) throws IOException  {
		ResourcesFacet resources = project.getFacet(ResourcesFacet.class);
		FileResource<?> resource = (FileResource<?>) resources
				.getResourceDirectory().getChild("development.properties");
		if(!resource.exists()){
			resource.createNewFile();			
		}
		Properties properties = new Properties();
		properties.load(resource.getResourceInputStream());
		properties.put("vraptor.simplemail.main.server","localhost");
		properties.put("vraptor.simplemail.main.port","25");
		properties.put("vraptor.simplemail.main.tls","false");
		properties.put("vraptor.simplemail.main.from","no-reply@myapp.com");
		properties.store(resource.getResourceOutputStream(),"simplemail");
	}	

	
}

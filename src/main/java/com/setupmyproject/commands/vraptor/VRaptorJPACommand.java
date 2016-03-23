package com.setupmyproject.commands.vraptor;

import org.jboss.forge.addon.javaee.cdi.CDIFacet_1_1;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.shrinkwrap.descriptor.api.beans11.BeansDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.commands.javaee.JPASetupCommand;
import com.setupmyproject.forge.ForceDependencyInstaller;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.MavenDependency;

public class VRaptorJPACommand {
	
	private Logger logger = LoggerFactory.getLogger(VRaptorJPACommand.class);
	
	

	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		logger.info("fazendo configuração do plugin jpa do vraptor");
		ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);
		installOnlyThePluginDependency(forgeHelper);
		installTransactionalDecorator(project);
		new JPASetupCommand().execute(project, addonRegistry, commandGenerators);
		
	}

	private void installTransactionalDecorator(Project project) {
		CDIFacet_1_1 cdiFacet = project.getFacet(CDIFacet_1_1.class);
		BeansDescriptor config = cdiFacet.getConfig();
		config.getOrCreateDecorators()
				.clazz("br.com.caelum.vraptor.jpa.TransactionDecorator");
		cdiFacet.saveConfig(config);
	}	


	private void installOnlyThePluginDependency(ForgeHelper forgeHelper) {
		ForceDependencyInstaller forceInstaller = forgeHelper
				.getForceDependencyInstaller();		
		forceInstaller.forceInstall(new MavenDependency(
				"br.com.caelum.vraptor", "vraptor-jpa", "4.0.3")
				.toDependencyBuilder());
		
	}

}

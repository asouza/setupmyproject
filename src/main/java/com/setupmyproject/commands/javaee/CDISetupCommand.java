package com.setupmyproject.commands.javaee;

import org.jboss.forge.addon.javaee.cdi.CDIFacet_1_1;
import org.jboss.forge.addon.javaee.servlet.ServletFacet_3_1;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.shrinkwrap.descriptor.api.webapp31.WebAppDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.forge.ForceDependencyInstaller;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.ServerEnvironmentAddon;

public class CDISetupCommand {

	private static final String WELD_LISTENER = "org.jboss.weld.environment.servlet.Listener";
	private Logger logger = LoggerFactory.getLogger(CDISetupCommand.class);

	public void execute(Project project, AddonRegistry addonRegistry, CommandGeneratorsQuery commandGenerators) {
		logger.info("Executando o comando de configuração do CDI");
		ForgeHelper forge = new ForgeHelper(project, addonRegistry);

		logger.debug("Instalando Facet do CDI 1.1");
		forge.installFacet(CDIFacet_1_1.class);
		
		logger.debug("Instalado Facet Servlet 3.1");
		ServletFacet_3_1 servletFacet = forge.installFacet(ServletFacet_3_1.class);
		WebAppDescriptor webXml = forge.getDescriptorFor(ServletFacet_3_1.class);
		
		ServerEnvironmentAddon serverEnvironmentAddon = forge.getServerEnvironment(commandGenerators);

		//TODO posso trocar pelo lance das mensagens?
		if(serverEnvironmentAddon.equals(ServerEnvironmentAddon.SERVLET_CONTAINER_3_X)){
			logger.debug("Configurando Listener do WELD no web.xml");
			webXml.createListener().listenerClass(WELD_LISTENER);
			servletFacet.saveConfig(webXml);
		}

		//TODO posso trocar pelo lance das mensagens?
		logger.debug("Configurando dependências do CDI no pom.xml");		
		ForceDependencyInstaller installer = forge.getForceDependencyInstaller();
		installer.forceInstall("javax.enterprise", "cdi-api", "1.1",serverEnvironmentAddon.getDependencyScope());
		installer.forceInstall("org.jboss.weld", "weld-core-impl", "2.1.2.Final",serverEnvironmentAddon.getDependencyScope());
		installer.forceInstall("org.jboss.weld.servlet", "weld-servlet", "2.1.2.Final",serverEnvironmentAddon.getDependencyScope());
	}

}

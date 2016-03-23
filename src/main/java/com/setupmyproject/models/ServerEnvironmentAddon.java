package com.setupmyproject.models;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.setupmyproject.commands.DefaultBeforeCommand;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.commands.messages.ServerEnvironmentMessage;
import com.setupmyproject.components.ProjectCommandFormItem;
import com.setupmyproject.controllers.forms.ServerEnvironmentAddonForm;
import com.setupmyproject.forge.ForgeHelper;

public enum ServerEnvironmentAddon implements Tooltipable,
		ProjectCommandFormItem {

	SERVER_JAVA_EE_7("provided", "/maven-dependencies/javaee7-server.xml"), SERVLET_CONTAINER_3_X(
			"compile", "/maven-dependencies/servlet-container-server.xml");

	private String dependencyScope;
	private String mavenFile;

	private ServerEnvironmentAddon(String dependencyScope, String mavenFile) {
		this.dependencyScope = dependencyScope;
		this.mavenFile = mavenFile;

	}

	public String getDependencyScope() {
		return dependencyScope;
	}

	private static Logger logger = LoggerFactory
			.getLogger(ServerEnvironmentAddon.class);

	@Override
	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		logger.info("Configurando o servidor escolhido");
		commandGenerators.messageToProjectCommand(
				ServerEnvironmentMessage.TYPE, this);
		new ForgeHelper(project, addonRegistry).getForceDependencyInstaller()
				.forceInstallFromXmlFile(mavenFile);
	}

	@Override
	public BigDecimal getPrice() {
		return new BigDecimal("3");
	}

	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(4, TimeUnit.MINUTES);
	}

	@Override
	public String getNameKey() {
		return "option." + name();
	}
	
	/**
	 * 
	 * @param commandGenerators
	 * @return retorna a lista de comandos do servidor. O invocador está interessado em rodar depois deles
	 */
	public static List<ProjectCommand> serverCommand(CommandGenerators commandGenerators) {
		Optional<ServerEnvironmentAddonForm> serverAddon = commandGenerators.ifFind(ServerEnvironmentAddonForm.class);
		if(serverAddon.isPresent()){
			return Lists.newArrayList(serverAddon.get().getEnvironmentAddon());			
		}
		return Arrays.asList(new DefaultBeforeCommand());
	}	

}

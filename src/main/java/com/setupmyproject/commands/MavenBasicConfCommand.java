package com.setupmyproject.commands;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.MetadataFacet;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.MavenDependency;

public class MavenBasicConfCommand implements ProjectCommand{

	private MavenSetupForm mavenSetupForm;
	private Logger logger = LoggerFactory.getLogger(MavenBasicConfCommand.class);

	public MavenBasicConfCommand(MavenSetupForm mavenSetupForm) {
		this.mavenSetupForm = mavenSetupForm;
	}

	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		logger.info("Configurando o maven");
		MetadataFacet facet = project.getFacet(MetadataFacet.class);
		MavenDependency mavenInfo = mavenSetupForm.getMavenInfo();
		facet.setProjectName(mavenInfo.getArtifactId());
		facet.setProjectVersion(mavenInfo.getVersion());
		facet.setProjectGroupName(mavenInfo.getGroupId());
	}

	@Override
	public BigDecimal getPrice() {
		return BigDecimal.ZERO;
	}
	
	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(5, TimeUnit.MINUTES);
	}
	
	

}

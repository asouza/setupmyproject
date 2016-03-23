package com.setupmyproject.commands.javaee;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import com.setupmyproject.forge.ForceDependencyInstaller;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.CommandGeneratorsQuery;

public class PrimeFacesCommand {

	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {

		ForgeHelper forge = new ForgeHelper(project, addonRegistry);
		ForceDependencyInstaller dependencyInstaller = forge
				.getForceDependencyInstaller();
		dependencyInstaller.forceInstall("org.primefaces", "primefaces", "5.2");
	}

}

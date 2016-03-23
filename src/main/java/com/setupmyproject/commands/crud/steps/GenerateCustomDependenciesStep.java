package com.setupmyproject.commands.crud.steps;

import java.util.HashMap;

import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;
import com.setupmyproject.models.crud.CrudModel;
import com.setupmyproject.models.crud.CrudModels;

public class GenerateCustomDependenciesStep implements CrudStep {

	@Override
	public boolean accepts(CrudModel model,ChoosenFrameworkCrudConfiguration conf) {
		return conf.getMavenDepsPath().isPresent();
	}

	@Override
	public void execute(ForgeHelper forgeHelper,
			HashMap<Object, Object> templatesParams, CrudModel model,
			MavenSetupForm mavenSetupForm, CrudModels models,ChoosenFrameworkCrudConfiguration conf) {
		if (!accepts(model,conf)) {
			throw new IllegalStateException(
					"Você não devia invocar o execute do GenerateCustomDepencies se a configuração falou que não tem deps.");
		}

		String mavenDepsPath = conf.getMavenDepsPath().get();
		forgeHelper.getForceDependencyInstaller().forceInstallFromXmlFile(
				mavenDepsPath);
	}

}

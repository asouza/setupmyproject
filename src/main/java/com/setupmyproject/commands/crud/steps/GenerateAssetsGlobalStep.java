package com.setupmyproject.commands.crud.steps;

import java.util.HashMap;

import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;

public class GenerateAssetsGlobalStep implements CrudGlobalStep{
	
	public ChoosenFrameworkCrudConfiguration conf;

	public GenerateAssetsGlobalStep(ChoosenFrameworkCrudConfiguration conf) {
		this.conf = conf;
	}

	@Override
	public void execute(ForgeHelper forgeHelper, MavenSetupForm mavenSetupForm,
			HashMap<Object, Object> templatesParams) {
		forgeHelper.copyFilesToCurrentProjectWebFolder(
				conf.getAssetsFolderPath(), "assets");		
	}
}
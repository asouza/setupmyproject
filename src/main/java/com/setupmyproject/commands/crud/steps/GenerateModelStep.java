package com.setupmyproject.commands.crud.steps;

import java.util.HashMap;

import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;
import com.setupmyproject.models.crud.CrudModel;
import com.setupmyproject.models.crud.CrudModels;

public class GenerateModelStep implements CrudStep {

	@Override
	public boolean accepts(CrudModel model,ChoosenFrameworkCrudConfiguration conf) {
		return true;
	}

	@Override
	public void execute(ForgeHelper forgeHelper,
			HashMap<Object, Object> templatesParams, CrudModel model,
			MavenSetupForm mavenSetupForm, CrudModels models,ChoosenFrameworkCrudConfiguration conf) {

		String modelsPackage = mavenSetupForm.packageFor("models");
		templatesParams.put("modelsPackage", modelsPackage);
		forgeHelper.getJavaSourceSaver().save(conf.getModelTemplate(),
				modelsPackage, templatesParams);
	}

}

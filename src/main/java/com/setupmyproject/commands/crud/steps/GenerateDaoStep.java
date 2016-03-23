package com.setupmyproject.commands.crud.steps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;
import com.setupmyproject.models.crud.CrudModel;
import com.setupmyproject.models.crud.CrudModels;

public class GenerateDaoStep implements CrudStep {

	@Override
	public boolean accepts(CrudModel model,ChoosenFrameworkCrudConfiguration conf) {
		return true;
	}

	@Override
	public void execute(ForgeHelper forgeHelper,
			HashMap<Object, Object> templatesParams, CrudModel model,
			MavenSetupForm mavenSetupForm, CrudModels models,ChoosenFrameworkCrudConfiguration conf) {

		String modelPackage = mavenSetupForm.packageFor("models."
				+ model.getName());
		List<String> imports = Arrays.asList(modelPackage);
		templatesParams.put("daoImports", imports);
		forgeHelper.getJavaSourceSaver().save(conf.getDaoTemplate(),
				mavenSetupForm.packageFor("daos"), templatesParams);
	}

}

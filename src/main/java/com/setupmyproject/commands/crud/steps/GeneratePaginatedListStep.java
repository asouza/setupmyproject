package com.setupmyproject.commands.crud.steps;

import java.util.HashMap;

import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;
import com.setupmyproject.models.crud.CrudModel;
import com.setupmyproject.models.crud.CrudModels;

/**
 * Step para quem precisa que seja gerado a classe que representa uma lista
 * paginada.
 * 
 * @author alberto
 *
 */
public class GeneratePaginatedListStep implements CrudStep {

	@Override
	public boolean accepts(CrudModel model,ChoosenFrameworkCrudConfiguration conf) {
		return conf.isANewProject();
	}

	@Override
	public void execute(ForgeHelper forgeHelper,
			HashMap<Object, Object> templatesParams, CrudModel model,
			MavenSetupForm mavenSetupForm, CrudModels models,ChoosenFrameworkCrudConfiguration conf) {

		forgeHelper.getJavaSourceSaver().save(
				"/templates/cruds/default-paginatedList.ftl",
				mavenSetupForm.packageFor("models"), templatesParams);
	}

}

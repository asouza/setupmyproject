package com.setupmyproject.commands.crud.steps;

import java.util.HashMap;

import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;
import com.setupmyproject.models.crud.CrudModel;
import com.setupmyproject.models.crud.CrudModels;

public class GenerateViewStep implements CrudStep{
		
	
	@Override
	public boolean accepts(CrudModel model,ChoosenFrameworkCrudConfiguration conf) {
		return model.needsViewController();
	}

	@Override
	public void execute(ForgeHelper forgeHelper,
			HashMap<Object, Object> templatesParams, CrudModel model,
			MavenSetupForm mavenSetupForm, CrudModels models,ChoosenFrameworkCrudConfiguration conf) {

		templatesParams.put("table", model.getDynamicTable());
		templatesParams.put("form", model.getForm(conf));

		forgeHelper.getViewCreator().create(conf.getListViewTemplatePath(),
				conf.getViewSaveConfiguration(model), templatesParams);

		forgeHelper.getViewCreator().create(conf.getAddFormViewTemplatePath(),
				conf.getViewSaveConfiguration(model), templatesParams);

		forgeHelper.getViewCreator().create(
				conf.getUpdateFormViewTemplatePath(),
				conf.getViewSaveConfiguration(model), templatesParams);

		forgeHelper.getViewCreator().create(
				conf.getInputsFormIncludeViewTemplatePath(),
				conf.getViewSaveConfiguration(model), templatesParams);		
	}

}

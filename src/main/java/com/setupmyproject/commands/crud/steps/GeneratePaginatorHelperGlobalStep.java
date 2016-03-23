package com.setupmyproject.commands.crud.steps;

import java.util.HashMap;

import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;

public class GeneratePaginatorHelperGlobalStep implements CrudGlobalStep{

	@Override
	public void execute(ForgeHelper forgeHelper, MavenSetupForm mavenSetupForm,
			HashMap<Object, Object> templatesParams) {
		
		forgeHelper.getJavaSourceSaver().save(
				"/templates/cruds/default-paginatedQueryHelper.ftl",
				mavenSetupForm.packageFor("daos"), templatesParams);		
	}

}

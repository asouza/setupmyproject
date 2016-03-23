package com.setupmyproject.commands.crud.steps;

import java.util.HashMap;
import java.util.Optional;

import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;

public class GenerateHeaderAndFooterGlobalStep implements CrudGlobalStep {

	private ChoosenFrameworkCrudConfiguration conf;

	public GenerateHeaderAndFooterGlobalStep(
			ChoosenFrameworkCrudConfiguration conf) {
		super();
		this.conf = conf;
	}

	@Override
	public void execute(ForgeHelper forgeHelper, MavenSetupForm mavenSetupForm,
			HashMap<Object, Object> templatesParams) {
		Optional<String> maybeHeader = conf.getHeaderViewTemplatePath();
		if (maybeHeader.isPresent()) {
			forgeHelper.getViewCreator().create(maybeHeader.get(),
					conf.getSharedIncludeSaveConfiguration(), templatesParams);
		}

		Optional<String> maybeFooter = conf.getFooterViewTemplatePath();
		if (maybeFooter.isPresent()) {
			forgeHelper.getViewCreator().create(maybeFooter.get(),
					conf.getSharedIncludeSaveConfiguration(), templatesParams);
		}
	}

}

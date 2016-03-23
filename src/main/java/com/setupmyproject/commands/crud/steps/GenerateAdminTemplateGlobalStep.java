package com.setupmyproject.commands.crud.steps;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.util.Assert;

import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.forge.ViewSaveConfiguration;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;

public class GenerateAdminTemplateGlobalStep implements CrudGlobalStep {

	private ChoosenFrameworkCrudConfiguration conf;

	public GenerateAdminTemplateGlobalStep(
			ChoosenFrameworkCrudConfiguration conf) {
		super();
		this.conf = conf;
	}

	@Override
	public void execute(ForgeHelper forgeHelper, MavenSetupForm mavenSetupForm,
			HashMap<Object, Object> templatesParams) {
		Assert.state(
				conf.getTemplateSaveConfiguration().isPresent() == conf
						.getTemplatePath().isPresent(),
				"Você deve configurar a pasta do template e o caminho para salvá-lo, ou não configurar nenhum dos dois");

		Optional<ViewSaveConfiguration> maybeTemplate = conf
				.getTemplateSaveConfiguration();
		if (maybeTemplate.isPresent()) {
			ViewSaveConfiguration templateFolder = maybeTemplate.get();

			forgeHelper.getViewCreator().create(conf.getTemplatePath().get(),
					templateFolder, "admin", templatesParams);

			conf.getPaginationComponentPath().ifPresent(
					paginationComponentPath -> {
						forgeHelper.getViewCreator().create(
								paginationComponentPath, templateFolder,
								"paginationComponent", templatesParams);
					});

		}
	}

}

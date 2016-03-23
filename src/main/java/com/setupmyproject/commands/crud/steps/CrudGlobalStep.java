package com.setupmyproject.commands.crud.steps;

import java.util.HashMap;

import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;

/**
 * Deve ser usado quando framework quiser fazer configurações globais para o crud
 * @author alberto
 *
 */
public interface CrudGlobalStep {

	void execute(ForgeHelper forgeHelper, MavenSetupForm mavenSetupForm,
			HashMap<Object, Object> templatesParams);

}

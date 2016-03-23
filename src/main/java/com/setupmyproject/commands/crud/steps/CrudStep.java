package com.setupmyproject.commands.crud.steps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;
import com.setupmyproject.models.crud.CrudModel;
import com.setupmyproject.models.crud.CrudModels;

/**
 * Os passos de execução da criação do CRUD foram dividos. Agora cada {@link ChoosenFrameworkCrudConfiguration} escolhe os seus
 * steps. Tem várias implementações padrões, como {@link GenerateControllerStep}, {@link GenerateCustomDependenciesStep}, {@link GenerateDaoStep}, {@link GenerateModelStep}
 * e a {@link GenerateViewStep}. 
 * @author alberto
 *
 */
public interface CrudStep {

	boolean accepts(CrudModel model,ChoosenFrameworkCrudConfiguration conf);

	void execute(ForgeHelper forgeHelper,
			HashMap<Object, Object> templatesParams, CrudModel model,
			MavenSetupForm mavenSetupForm, CrudModels models,ChoosenFrameworkCrudConfiguration conf);
	
	public static List<CrudStep> defaultCrudSteps(){
		ArrayList<CrudStep> steps = new ArrayList<CrudStep>();
		steps.add(new GenerateViewStep());
		steps.add(new GenerateControllerStep());
		steps.add(new GenerateModelStep());
		steps.add(new GenerateDaoStep());
		steps.add(new GenerateCustomDependenciesStep());
		return steps;
	}

}

package com.setupmyproject.commands.crud;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.commands.crud.steps.CrudGlobalStep;
import com.setupmyproject.commands.crud.steps.CrudStep;
import com.setupmyproject.commands.messages.JPAMessages;
import com.setupmyproject.controllers.forms.CrudForm;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.models.CommandGenerators;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.JavaEEAddon;
import com.setupmyproject.models.VRaptorAddon;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;
import com.setupmyproject.models.crud.CrudModel;
import com.setupmyproject.models.crud.CrudModels;

public class CrudGeneratorCommand implements ProjectCommand {

	private ChoosenFrameworkCrudConfiguration conf;
	private Logger logger = LoggerFactory.getLogger(CrudGeneratorCommand.class);

	public CrudGeneratorCommand(ChoosenFrameworkCrudConfiguration conf) {
		this.conf = conf;
	}

	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandQuery) {
		logger.info("Gerando Crud para os modelos selecionados");

		CrudForm crudForm = commandQuery.find(CrudForm.class);
		CrudModels models = crudForm.getCrudModels();
		ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);

		HashMap<Object, Object> templatesParams = new HashMap<>();
		templatesParams.put("crudModels", models);
		templatesParams.put("frameworkConf", conf);
		templatesParams.put("jsfExpression", new DynamicExpression("#"));
		templatesParams.put("jspExpression", new DynamicExpression("$"));
		MavenSetupForm mavenSetupForm = commandQuery.find(MavenSetupForm.class);
		templatesParams.put("mavenForm", mavenSetupForm);

		if (conf.isANewProject()) {
			List<CrudGlobalStep> globalSteps = conf.getCrudGlobalSteps();
			for(CrudGlobalStep globalStep : globalSteps){
				globalStep.execute(forgeHelper,mavenSetupForm,templatesParams);
			}
		}
		

		List<CrudStep> crudSteps = conf.getCrudSteps();

		for (CrudModel model : models) {
			commandQuery.messageToProjectCommand(JPAMessages.ADD_CLASS,
					mavenSetupForm.packageFor("models." + model.getName()));
			
			templatesParams.put("currentCrudModel", model);
			templatesParams.put("relatedCrudModels",
					model.getAllRelatedCrudModels(models));

			executeCrudSteps(models, forgeHelper, templatesParams,
					mavenSetupForm, crudSteps, model);
		}
	}

	private void executeCrudSteps(CrudModels models, ForgeHelper forgeHelper,
			HashMap<Object, Object> templatesParams,
			MavenSetupForm mavenSetupForm, List<CrudStep> crudSteps,
			CrudModel model) {
		crudSteps
				.stream()
				.filter((step) -> step.accepts(model, conf))
				.forEach(
						(step) -> step.execute(forgeHelper, templatesParams,
								model, mavenSetupForm, models, conf));
	}

	@Override
	public BigDecimal getPrice() {
		return BigDecimal.ONE;
	}

	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(30, TimeUnit.MINUTES);
	}

	@Override
	public List<ProjectCommand> before(CommandGenerators commandGenerators) {
		
		ArrayList<ProjectCommand> jpaCommands = Lists.newArrayList();
		
		Optional<? extends VRaptorAddon> vraptorJPACommand = commandGenerators
				.ifFindProjectCommand(VRaptorAddon.VRAPTOR_JPA.getClass());
		
		if (vraptorJPACommand.isPresent()) {
			jpaCommands.add(vraptorJPACommand.get());
		}
		
		Optional<? extends JavaEEAddon> jsfJPACommand = commandGenerators.ifFindProjectCommand(JavaEEAddon.JPA.getClass());
		if(jsfJPACommand.isPresent()){
			jpaCommands.add(jsfJPACommand.get());			
		}
		
				
		return jpaCommands.isEmpty() ? ProjectCommand.super.before(commandGenerators) : jpaCommands;
	}

}

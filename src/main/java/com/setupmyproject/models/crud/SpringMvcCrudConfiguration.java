package com.setupmyproject.models.crud;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.setupmyproject.commands.crud.steps.CrudGlobalStep;
import com.setupmyproject.commands.crud.steps.CrudStep;
import com.setupmyproject.commands.crud.steps.GenerateAdminTemplateGlobalStep;
import com.setupmyproject.commands.crud.steps.GenerateAssetsGlobalStep;
import com.setupmyproject.commands.crud.steps.GenerateHeaderAndFooterGlobalStep;
import com.setupmyproject.commands.crud.steps.GeneratePaginatedListStep;
import com.setupmyproject.commands.crud.steps.GeneratePaginatorHelperGlobalStep;
import com.setupmyproject.components.html.DefaultHtmlEngineProcessor;
import com.setupmyproject.components.html.DefaultHtmlPartProcessorGroup;
import com.setupmyproject.components.html.HtmlEngineProcessor;
import com.setupmyproject.components.html.MetaData;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;
import com.setupmyproject.components.html.spring.SpringFormElementProcessor;
import com.setupmyproject.components.html.spring.SpringHtmlFormPartProcessorGroup;
import com.setupmyproject.forge.ViewSaveConfiguration;

public class SpringMvcCrudConfiguration implements
		ChoosenFrameworkCrudConfiguration {

	@Override
	public HtmlEngineProcessor getTableProcessor() {
		return new DefaultHtmlEngineProcessor(
				new DefaultHtmlPartProcessorGroup());
	}

	@Override
	public String getListViewTemplatePath() {
		return "/templates/cruds/list.ftl";
	}

	@Override
	public ViewSaveConfiguration getViewSaveConfiguration(CrudModel crudModel) {
		return new ViewSaveConfiguration("WEB-INF/views/"
				+ crudModel.getModuleName() + "/", "jsp");
	}

	@Override
	public ViewSaveConfiguration getSharedIncludeSaveConfiguration() {
		return new ViewSaveConfiguration("WEB-INF/views/", "jsp");
	}

	@Override
	public HtmlEngineProcessor getFormProcessor() {
		return new DefaultHtmlEngineProcessor(
				new SpringHtmlFormPartProcessorGroup());
	}

	@Override
	public String getAddFormViewTemplatePath() {
		return "/templates/cruds/springmvc/pages/form-add.ftl";
	}

	@Override
	public String getUpdateFormViewTemplatePath() {
		return "/templates/cruds/springmvc/pages/form-update.ftl";
	}

	@Override
	public List<Entry<String, Object>> getFormMetaData(CrudModel crudModel) {
		return Arrays.asList(MetaData.attribute(
				SpringFormElementProcessor.COMMAND_NAME_ATTRIBUTE,
				crudModel.getClassNameAsProperty()));
	}

	@Override
	public String getControllerTemplate() {
		return "/templates/cruds/springmvc/classes/controller.ftl";
	}

	@Override
	public String getDaoTemplate() {
		return "/templates/cruds/springmvc/classes/dao.ftl";
	}

	@Override
	public String getInputsFormIncludeViewTemplatePath() {
		return "/templates/cruds/springmvc/pages/form-inputs.ftl";
	}

	@Override
	public Optional<String> getMavenDepsPath() {
		return Optional.of("/templates/cruds/springmvc/deps.xml");
	}

	@Override
	public Optional<String> getHeaderViewTemplatePath() {
		return Optional.empty();
	}

	@Override
	public Optional<String> getFooterViewTemplatePath() {
		return Optional.empty();
	}

	@Override
	public void handleViewElement(ViewElement input, CrudField f,
			CrudModel crudModel) {
		SpringHtmlFormPartProcessorGroup group = new SpringHtmlFormPartProcessorGroup();
		Optional<ViewPartProcessor> possibleProcessor = group.supports(input);
		if (possibleProcessor.isPresent()) {
			possibleProcessor.get().prepareInputMetaData(input, f, crudModel);
		}
	}
	
	@Override
	public Optional<ViewSaveConfiguration> getTemplateSaveConfiguration() {
		return Optional.of(new ViewSaveConfiguration("WEB-INF/tags/template/", "tag"));
	}

	@Override
	public Optional<String> getTemplatePath() {
		return Optional.of("/templates/cruds/admin-template.ftl");
	}	
	
	@Override
	public List<CrudStep> getCrudSteps() {
		List<CrudStep> steps = CrudStep.defaultCrudSteps();
		steps.add(new GeneratePaginatedListStep());
		return steps;
	}

	@Override
	public List<CrudGlobalStep> getCrudGlobalSteps() {
		GenerateAssetsGlobalStep assetsGlobalStep = new GenerateAssetsGlobalStep(this);
		GenerateHeaderAndFooterGlobalStep headerAndFooterGlobalStep = new GenerateHeaderAndFooterGlobalStep(this);
		GenerateAdminTemplateGlobalStep adminTemplateGlobalStep = new GenerateAdminTemplateGlobalStep(this);
		GeneratePaginatorHelperGlobalStep paginatorHelperGlobalStep = new GeneratePaginatorHelperGlobalStep();
		return Lists.newArrayList(assetsGlobalStep,headerAndFooterGlobalStep,adminTemplateGlobalStep,paginatorHelperGlobalStep);
	}	
}

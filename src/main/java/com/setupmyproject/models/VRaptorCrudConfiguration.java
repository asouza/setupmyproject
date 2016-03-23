package com.setupmyproject.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.setupmyproject.components.html.HtmlEngineProcessor;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;
import com.setupmyproject.components.html.vraptor.VRaptorHtmlFormPartProcessorGroup;
import com.setupmyproject.forge.ViewSaveConfiguration;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;
import com.setupmyproject.models.crud.CrudField;
import com.setupmyproject.models.crud.CrudModel;

public class VRaptorCrudConfiguration implements ChoosenFrameworkCrudConfiguration{

	@Override
	public HtmlEngineProcessor getTableProcessor() {
		return new DefaultHtmlEngineProcessor(new VRaptorHtmlFormPartProcessorGroup());
	}

	@Override
	public String getListViewTemplatePath() {
		return "/templates/cruds/list.ftl";
	}

	@Override
	public ViewSaveConfiguration getViewSaveConfiguration(CrudModel crudModel) {
		return new ViewSaveConfiguration("WEB-INF/jsp/"
				+ crudModel.getClassNameAsProperty() + "/", "jsp");
	}

	@Override
	public HtmlEngineProcessor getFormProcessor() {
		return new DefaultHtmlEngineProcessor(new VRaptorHtmlFormPartProcessorGroup());
	}

	@Override
	public String getAddFormViewTemplatePath() {
		return "/templates/cruds/vraptor/pages/formAdd.ftl";
	}

	@Override
	public List<Entry<String, Object>> getFormMetaData(CrudModel crudModel) {
		return new ArrayList<Map.Entry<String,Object>>();
	}

	@Override
	public String getControllerTemplate() {
		return "/templates/cruds/vraptor/classes/controller.ftl";
	}

	@Override
	public String getDaoTemplate() {
		return "/templates/cruds/vraptor/classes/dao.ftl";
	}

	@Override
	public String getUpdateFormViewTemplatePath() {
		return "/templates/cruds/vraptor/pages/formUpdate.ftl";
	}

	@Override
	public String getInputsFormIncludeViewTemplatePath() {
		return "/templates/cruds/vraptor/pages/form-inputs.ftl";
	}

	@Override
	public Optional<String> getMavenDepsPath() {
		return Optional.empty();
	}

	@Override
	public ViewSaveConfiguration getSharedIncludeSaveConfiguration() {
		return new ViewSaveConfiguration("WEB-INF/jsp/", "jsp");
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
		VRaptorHtmlFormPartProcessorGroup group = new VRaptorHtmlFormPartProcessorGroup();
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

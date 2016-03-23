package com.setupmyproject.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.setupmyproject.commands.crud.steps.CrudGlobalStep;
import com.setupmyproject.commands.crud.steps.GenerateAdminTemplateGlobalStep;
import com.setupmyproject.commands.crud.steps.GenerateAssetsGlobalStep;
import com.setupmyproject.commands.crud.steps.GenerateHeaderAndFooterGlobalStep;
import com.setupmyproject.components.html.DefaultHtmlEngineProcessor;
import com.setupmyproject.components.html.HtmlEngineProcessor;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.components.html.ViewPartProcessor;
import com.setupmyproject.components.html.jsf.JSFHtmlFormPartProcessorGroup;
import com.setupmyproject.forge.ViewSaveConfiguration;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;
import com.setupmyproject.models.crud.CrudField;
import com.setupmyproject.models.crud.CrudModel;

public class JSFCrudConfiguration implements ChoosenFrameworkCrudConfiguration{

	@Override
	public HtmlEngineProcessor getTableProcessor() {
		return new DefaultHtmlEngineProcessor(new JSFHtmlFormPartProcessorGroup());
	}

	@Override
	public String getListViewTemplatePath() {
		return "/templates/cruds/jsf/pages/list.ftl";
	}

	@Override
	public ViewSaveConfiguration getViewSaveConfiguration(CrudModel crudModel) {
		return new ViewSaveConfiguration(crudModel.getModuleName() + "/", "xhtml");
	}

	@Override
	public HtmlEngineProcessor getFormProcessor() {
		return new DefaultHtmlEngineProcessor(new JSFHtmlFormPartProcessorGroup());
	}

	@Override
	public String getAddFormViewTemplatePath() {
		return "/templates/cruds/jsf/pages/form-add.ftl";
	}

	@Override
	public List<Entry<String, Object>> getFormMetaData(CrudModel crudModel) {
		return new ArrayList<Map.Entry<String,Object>>();
	}

	@Override
	public String getControllerTemplate() {
		return "/templates/cruds/jsf/classes/controller.ftl";
	}

	@Override
	public String getDaoTemplate() {
		return "/templates/cruds/jsf/classes/dao.ftl";
	}

	@Override
	public String getUpdateFormViewTemplatePath() {
		return "/templates/cruds/jsf/pages/form-update.ftl";
	}

	@Override
	public String getInputsFormIncludeViewTemplatePath() {
		return "/templates/cruds/jsf/pages/form-inputs.ftl";
	}

	@Override
	public Optional<String> getMavenDepsPath() {
		return Optional.of("/templates/cruds/jsf/deps.xml");
	}

	@Override
	public ViewSaveConfiguration getSharedIncludeSaveConfiguration() {
		return new ViewSaveConfiguration("shared/", "xhtml");
	}

	@Override
	public Optional<String> getHeaderViewTemplatePath() {
		//aqui foi um gato para suportar o template...
		return Optional.of("/templates/cruds/jsf/pages/crud-template.ftl");
	}

	@Override
	public Optional<String> getFooterViewTemplatePath() {
		return Optional.empty();
	}

	@Override
	public void handleViewElement(ViewElement input, CrudField f,
			CrudModel crudModel) {
		JSFHtmlFormPartProcessorGroup group = new JSFHtmlFormPartProcessorGroup();
		Optional<ViewPartProcessor> possibleProcessor = group.supports(input);
		if (possibleProcessor.isPresent()) {
			possibleProcessor.get().prepareInputMetaData(input, f, crudModel);
		}		
	}

	@Override
	public Optional<ViewSaveConfiguration> getTemplateSaveConfiguration() {
		return Optional.empty();
	}

	@Override
	public Optional<String> getTemplatePath() {
		return Optional.empty();
	}

	@Override
	public String getAssetsFolderPath() {
		return "/templates/cruds/assets-jsf";
	}

	@Override
	public List<CrudGlobalStep> getCrudGlobalSteps() {
		GenerateAssetsGlobalStep assetsGlobalStep = new GenerateAssetsGlobalStep(this);
		GenerateHeaderAndFooterGlobalStep headerAndFooterGlobalStep = new GenerateHeaderAndFooterGlobalStep(this);
		GenerateAdminTemplateGlobalStep adminTemplateGlobalStep = new GenerateAdminTemplateGlobalStep(this);
		
		return Lists.newArrayList(assetsGlobalStep,headerAndFooterGlobalStep,adminTemplateGlobalStep);
	}
}

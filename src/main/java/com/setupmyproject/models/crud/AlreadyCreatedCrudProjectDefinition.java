package com.setupmyproject.models.crud;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.Lists;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.crud.steps.CrudGlobalStep;
import com.setupmyproject.commands.crud.steps.CrudStep;
import com.setupmyproject.components.html.HtmlEngineProcessor;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.forge.ViewSaveConfiguration;
import com.setupmyproject.models.ProjectDefinition;
import com.setupmyproject.models.ProjectType;
import com.setupmyproject.models.SetupState;
import com.setupmyproject.wizards.Wizard;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class AlreadyCreatedCrudProjectDefinition implements ProjectDefinition {

	private ProjectType projectType;
	
	/**
	 * @deprecated frameworks only
	 */
	public AlreadyCreatedCrudProjectDefinition(){
		
	}

	public AlreadyCreatedCrudProjectDefinition(ProjectType projectType) {
		Assert.isTrue(
				projectType.getCrudFrameworkConfiguration().isPresent(),
				"Toda configuração de projeto existente, deveria ter um CrudFramework selecionado");
		this.projectType = projectType;
	}

	@Override
	public Map<Class<?>, Function<SetupState, Wizard>> steps() {
		return projectType.steps();
	}

	@Override
	public ProjectCommand getCommand() {
		return projectType.getCommand();
	}

	@Override
	public Optional<ChoosenFrameworkCrudConfiguration> getCrudFrameworkConfiguration() {
		return Optional.of(new AlreadyCreatedCrudProjectCrudConfiguration(
				projectType.getCrudFrameworkConfiguration().get()));
	}
	
	@Override
	public boolean isANewProject() {
		return false;
	}

	public static class AlreadyCreatedCrudProjectCrudConfiguration implements
			ChoosenFrameworkCrudConfiguration {
		private ChoosenFrameworkCrudConfiguration delegate;

		public AlreadyCreatedCrudProjectCrudConfiguration(
				ChoosenFrameworkCrudConfiguration delegate) {
			super();
			this.delegate = delegate;
		}

		public HtmlEngineProcessor getTableProcessor() {
			return delegate.getTableProcessor();
		}

		public String getListViewTemplatePath() {
			return delegate.getListViewTemplatePath();
		}

		public ViewSaveConfiguration getViewSaveConfiguration(
				CrudModel crudModel) {
			return delegate.getViewSaveConfiguration(crudModel);
		}

		public HtmlEngineProcessor getFormProcessor() {
			return delegate.getFormProcessor();
		}

		public String getAddFormViewTemplatePath() {
			return delegate.getAddFormViewTemplatePath();
		}

		public List<Entry<String, Object>> getFormMetaData(CrudModel crudModel) {
			return delegate.getFormMetaData(crudModel);
		}

		public String getControllerTemplate() {
			return delegate.getControllerTemplate();
		}

		public String getDaoTemplate() {
			return delegate.getDaoTemplate();
		}

		public String getModelTemplate() {
			return delegate.getModelTemplate();
		}

		public String getUpdateFormViewTemplatePath() {
			return delegate.getUpdateFormViewTemplatePath();
		}

		public String getInputsFormIncludeViewTemplatePath() {
			return delegate.getInputsFormIncludeViewTemplatePath();
		}

		public Optional<String> getMavenDepsPath() {
			return delegate.getMavenDepsPath();
		}

		public ViewSaveConfiguration getSharedIncludeSaveConfiguration() {
			return delegate.getSharedIncludeSaveConfiguration();
		}

		public Optional<String> getHeaderViewTemplatePath() {
			return delegate.getHeaderViewTemplatePath();
		}

		public Optional<String> getFooterViewTemplatePath() {
			return delegate.getFooterViewTemplatePath();
		}

		public void handleViewElement(ViewElement input, CrudField f,
				CrudModel crudModel) {
			delegate.handleViewElement(input, f, crudModel);
		}

		public List<CrudStep> getCrudSteps() {
			return delegate.getCrudSteps();
		}

		public boolean isANewProject() {
			return false;
		}

		@Override
		public Optional<ViewSaveConfiguration> getTemplateSaveConfiguration() {
			return delegate.getTemplateSaveConfiguration();
		}

		@Override
		public Optional<String> getTemplatePath() {
			return delegate.getTemplatePath();
		}

		@Override
		public List<CrudGlobalStep> getCrudGlobalSteps() {
			return Lists.newArrayList();
		}

	}

}

package com.setupmyproject.models.crud;

import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import com.setupmyproject.commands.crud.steps.CrudGlobalStep;
import com.setupmyproject.commands.crud.steps.CrudStep;
import com.setupmyproject.components.html.HtmlEngineProcessor;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.forge.ViewSaveConfiguration;

public interface ChoosenFrameworkCrudConfiguration {

	HtmlEngineProcessor getTableProcessor();

	/**
	 * 
	 * @return caminho no classpath para o template de view.
	 *         Ex:/templates/cruds/springmvc/list.ftl
	 */
	String getListViewTemplatePath();

	/**
	 * 
	 * @param crudModel
	 *            model usado para criar a pasta de views
	 * @return
	 */
	ViewSaveConfiguration getViewSaveConfiguration(CrudModel crudModel);

	/**
	 * 
	 * @return o possível caminho para gravar o template
	 */
	Optional<ViewSaveConfiguration> getTemplateSaveConfiguration();

	/**
	 * Esse método deve funcionar sincronizado com o
	 * {@link #getTemplateSaveConfiguration()}
	 * 
	 * @return o path do template em si.
	 */
	Optional<String> getTemplatePath();

	HtmlEngineProcessor getFormProcessor();

	/**
	 * 
	 * @return caminho no classpath para o template de view.
	 *         Ex:/templates/cruds/springmvc/form-add.ftl
	 */
	String getAddFormViewTemplatePath();

	List<Entry<String, Object>> getFormMetaData(CrudModel crudModel);

	/**
	 * 
	 * @return caminho no classpath para o template de view.
	 *         Ex:/templates/cruds/springmvc/controller.ftl
	 */
	String getControllerTemplate();

	String getDaoTemplate();

	default String getModelTemplate() {
		return "/templates/cruds/default-model.ftl";
	}

	/**
	 * 
	 * @return caminho no classpath para o template de view.
	 *         Ex:/templates/cruds/springmvc/form-update.ftl
	 */
	String getUpdateFormViewTemplatePath();

	/**
	 * Usado para gerar o trecho de página com os inputs do formulário
	 * 
	 * @return caminho no classpath para o template de view.
	 *         Ex:/templates/cruds/springmvc/form-update.ftl
	 */
	String getInputsFormIncludeViewTemplatePath();

	/**
	 * 
	 * @return caso tenha, retorna caminho do arquivo com as dependencias
	 *         necessárias para o crud. Ex:"/templates/cruds/springmvc/deps.xml"
	 */
	Optional<String> getMavenDepsPath();

	/**
	 * 
	 * @return a {@link ViewSaveConfiguration} relativa a possiveis includes e
	 *         outras views compartilhadas.
	 */
	ViewSaveConfiguration getSharedIncludeSaveConfiguration();

	Optional<String> getHeaderViewTemplatePath();

	Optional<String> getFooterViewTemplatePath();

	void handleViewElement(ViewElement input, CrudField f, CrudModel crudModel);

	default List<CrudStep> getCrudSteps() {
		return CrudStep.defaultCrudSteps();
	}

	/**
	 * 
	 * @return se é para gerar ou não os recursos estáticos da aplicação
	 */
	default boolean isANewProject() {
		return true;
	}

	/**
	 * 
	 * @return a pasta contendo os arquivos estáticos que devem ser importados
	 *         para o novo projeto a ser criado.
	 *         Ex:/templates/cruds/assets-actionbased
	 */
	default String getAssetsFolderPath() {
		return "/templates/cruds/assets-actionbased";
	}

	/** 
	 * @return o path do template em si.Caso exista um componente de paginação
	 */
	default Optional<String> getPaginationComponentPath() {
		return Optional.of("/templates/cruds/paginationComponent.ftl");
	}

	List<CrudGlobalStep> getCrudGlobalSteps();

}

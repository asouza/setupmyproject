package com.setupmyproject.models;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.jboss.forge.addon.facets.FacetFactory;
import org.jboss.forge.addon.projects.Project;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;
import com.setupmyproject.wizards.Wizard;

/**
 * Interface que define tudo necessario para a criacao de um projeto. O método nextPage sobrou, mas dá pra levar.
 * @author alberto
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface ProjectDefinition {

	/**
	 * 
	 * @return mapa com os passos que devem ser seguidos para configurar o
	 *         projeto
	 */	
	public Map<Class<?>, Function<SetupState,Wizard>> steps();
	
	public abstract ProjectCommand getCommand();

	String name();
	
	@JsonIgnore
	public default boolean isPayed() {
		return true;
	}
	
	@JsonIgnore
	public default boolean isANewProject() {
		return true;
	}
	
	/**
	 * Caso este projeto precise adicionar alguma coisa a mais no Forge
	 * @param facetFactory
	 * @param project
	 */
	public default void prepareFacets(FacetFactory facetFactory, Project project) {
	}	
	
	@JsonIgnore
	public default Optional<ChoosenFrameworkCrudConfiguration> getCrudFrameworkConfiguration() {
		return Optional.empty();
	}	
	
	public default ModelAndView nextPage(Class<?> currentController,
			ApplicationContext applicationContext, SetupState setupState) {
		Wizard nextWizard = steps().get(currentController).apply(setupState);
		Assert.notNull(nextWizard,
				"O currentController " + currentController.getName()
						+ " não está associado com nenhum wizard do projeto "
						+ this);
		return nextWizard.getModelAndView(applicationContext);

	}	
}

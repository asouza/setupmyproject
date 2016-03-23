package com.setupmyproject.components;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.models.ProjectDefinition;
import com.setupmyproject.models.ProjectType;
import com.setupmyproject.models.SetupState;

@Component
public class StepDiscovery {

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private OngoinSetupState ongoinSetupState;

	/**
	 * 
	 * @param currentController
	 *            controller que está sendo executado no momento
	 * @param currentForm
	 *            objeto que representa o form sendo manipulado no momento
	 * @return ModelAndView apontando para a próxima página e com o setupState
	 *         atualizado.
	 */
	public ModelAndView nextPage(Object currentController,
			Object currentForm) {
		SetupState previousState = ongoinSetupState.getSetupState();
		return resolveNextPage(currentController, currentForm, previousState);
	}

	private ModelAndView resolveNextPage(Object currentController,
			Object currentForm, SetupState previousState) {
		ProjectDefinition projectDefinition = previousState.getProjectType();
		SetupState newState = previousState.addConf(currentForm,(state) -> {
			ongoinSetupState.updateState(state);
			return null;
		});
		ModelAndView page = projectDefinition.nextPage(currentController.getClass(),
				applicationContext,newState);
		Assert.notNull(page, "Esqueceu de retornar o modelAndView do wizard associado ao controller "+currentController+" ?");
		page.addObject("setupState", newState);
		return page;
	}

	/**
	 * 
	 * @param currentController controller que está sendo executado no momento
	 * @param currentForm objeto que representa o form sendo manipulado no momento
	 * @param bindingResult necessário para verificar se teve erros
	 * @param validationFunction função com o código necessário para voltar para a tela de erro de validação
	 * @return
	 */
	public ModelAndView nextPage(Object currentController,
			Object currentForm, BindingResult bindingResult,
			Callable<ModelAndView> validationFunction) {
		SetupState newState = ongoinSetupState.getSetupState();
		if (bindingResult.hasErrors()) {
			try {
				ModelAndView modelAndView = validationFunction.call();
				modelAndView.addObject("setupState",newState);
				return modelAndView;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return resolveNextPage(currentController, currentForm, newState);
	}

}

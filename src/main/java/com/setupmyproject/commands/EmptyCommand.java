package com.setupmyproject.commands;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import com.setupmyproject.controllers.forms.CommandGenerator;
import com.setupmyproject.models.CommandGeneratorsQuery;

/**
 * Basicamente deve ser usado quando um {@link CommandGenerator} não tiver nada para gerar. Por exemplo, nenhuma opção 
 * do formulário foi escolhida, então retorna um NullObject, aí não precisa dar tratamento especial mais na frente.
 * @author alberto
 *
 */
@UserInvisible
public class EmptyCommand implements ProjectCommand{

	@Override
	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
	}
	
	@Override
	public String toString() {
		return this.getClass().getName();
	}

	@Override
	public BigDecimal getPrice() {
		return BigDecimal.ZERO;
	}
	
	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(0, TimeUnit.SECONDS);
	}
	
	

}

package com.setupmyproject.commands;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import com.setupmyproject.models.CommandGeneratorsQuery;

/**
 * Apenas para ser usado como antes ou depois de comandos que precisam de alguma ordem.
 * @author alberto
 *
 */
@UserInvisible
public class DefaultBeforeCommand implements ProjectCommand{

	@Override
	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
	}
	
	@Override
	public String toString() {
		return "["+getClass().getSimpleName()+"]";
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

package com.setupmyproject.models;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.commands.deltaspike.DeltaSpikeJPACommandGenerator;
import com.setupmyproject.components.ProjectCommandFormItem;

public enum DeltaSpikeAddon implements Tooltipable,ProjectCommandFormItem{

	DELTA_SPIKE_JPA {

		@Override
		public void execute(Project project, AddonRegistry addonRegistry,
				CommandGeneratorsQuery commandGenerators) {
			new DeltaSpikeJPACommandGenerator().execute(project,addonRegistry,commandGenerators);
		}
		
	};


	@Override
	public BigDecimal getPrice() {
		return new BigDecimal("1");
	}

	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(10, TimeUnit.MINUTES);
	}
	
	public String getNameKey(){
		return "option."+name();
	}	
}

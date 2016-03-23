package com.setupmyproject.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.commands.vraptor.VRaptorJPACommand;
import com.setupmyproject.commands.vraptor.VRaptorJava8Command;
import com.setupmyproject.commands.vraptor.VRaptorSimpleMailCommand;
import com.setupmyproject.components.ProjectCommandFormItem;

public enum VRaptorAddon implements ProjectCommandFormItem,Tooltipable{

	VRAPTOR_JPA {
		@Override
		public void execute(Project project,
				AddonRegistry addonRegistry,
				CommandGeneratorsQuery commandGenerators) {
			new VRaptorJPACommand().execute(project,addonRegistry,commandGenerators);
		}
		
		@Override
		public TimeToExecute getTimeToExecute() {
			return new TimeToExecute(35, TimeUnit.MINUTES);
		}
	},VRAPTOR_JAVA8 {
		//TODO se o cara tiver escolhido o java7, não tem pq mostrar esse addon. 
		@Override
		public void execute(Project project,
				AddonRegistry addonRegistry,
				CommandGeneratorsQuery commandGenerators) {
			new VRaptorJava8Command().execute(project,addonRegistry,commandGenerators);
		}
		
		@Override
		public TimeToExecute getTimeToExecute() {
			return new TimeToExecute(5, TimeUnit.MINUTES);
		}
	},VRAPTOR_SIMPLEMAIL {
		@Override
		public void execute(Project project,
				AddonRegistry addonRegistry,
				CommandGeneratorsQuery commandGenerators) {
			new VRaptorSimpleMailCommand().execute(project,addonRegistry,commandGenerators);
		}
		
		@Override
		public TimeToExecute getTimeToExecute() {
			return new TimeToExecute(20, TimeUnit.MINUTES);
	 	}
	};
	
	@Override
	public BigDecimal getPrice() {
		return BigDecimal.ZERO;
	}
	
	@Override
	public String getNameKey() {
		return "option."+name();
	}	
}

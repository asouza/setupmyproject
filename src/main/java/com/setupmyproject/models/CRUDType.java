package com.setupmyproject.models;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.components.ProjectCommandFormItem;

public enum CRUDType implements Tooltipable, ProjectCommandFormItem {

	CRUD_PRODUCT {
		public Optional<String> getModelFile() {
			return  Optional.of("/crud-models/product-model.xml");
		}

	},

	CRUD_CUSTOM {
		public Optional<String> getModelFile() {
			return Optional.empty();
		}
	};

	@Override
	public BigDecimal getPrice() {
		return BigDecimal.ONE;
	}

	/**
	 * 
	 * @return o arquivo de modelo base, caso seja um fixo.
	 */
	abstract public Optional<String> getModelFile();

	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(30, TimeUnit.MINUTES);
	}

	@Override
	public String getNameKey() {
		return "option." + name();
	}

	@Override
	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
	}

}

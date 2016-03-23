package com.setupmyproject.controllers.forms;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.existingproject.ExistingProjectSetupCommand;
import com.setupmyproject.models.HasProjectTypeDefinition;
import com.setupmyproject.models.ProjectDefinition;
import com.setupmyproject.models.ProjectType;
import com.setupmyproject.models.crud.AlreadyCreatedCrudProjectDefinition;

public class ExistingConfigurationForm implements HasProjectTypeDefinition {

	@NotNull
	private ProjectType projectType;

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

	@Override
	public List<? extends ProjectCommand> createComand() {
		return Arrays.asList(new ExistingProjectSetupCommand(projectType));
	}

	@Override
	@JsonIgnore
	public ProjectDefinition getProjectDefinition() {
		return new AlreadyCreatedCrudProjectDefinition(projectType);
	}

	@Override
	public String toString() {
		return "ExistingConfigurationForm [projectType=" + projectType + "]";
	}

	
}

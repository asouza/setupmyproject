package com.setupmyproject.controllers.forms;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.models.HasProjectTypeDefinition;
import com.setupmyproject.models.ProjectDefinition;
import com.setupmyproject.models.ProjectType;


public class ConfigurationForm implements HasProjectTypeDefinition{

	@NotNull
	private ProjectType projectType;

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projecType) {
		this.projectType = projecType;
	}

	@Override
	public String toString() {
		return "ConfigurationForm [projectType=" + projectType + "]";
	}
	
	public List<ProjectCommand> createComand(){
		return Arrays.asList(projectType.getCommand());
	}

	@Override
	@JsonIgnore
	public ProjectDefinition getProjectDefinition() {
		return projectType;
	}

	
}

package com.setupmyproject.controllers.forms;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.crud.CrudGeneratorCommand;
import com.setupmyproject.infra.SetupMyProjectXMLReader;
import com.setupmyproject.infra.StringReaderXmlSource;
import com.setupmyproject.models.CRUDType;
import com.setupmyproject.models.ProjectDefinition;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;
import com.setupmyproject.models.crud.CrudModels;

public class CrudForm implements CommandGenerator {

	private CRUDType crudType;
	private ProjectDefinition projectType;
	private String customModelXml;

	@JsonIgnore
	public boolean isCustomCrudSelected() {
		return crudType != null && crudType.equals(CRUDType.CRUD_CUSTOM);
	}

	public void setCustomModelXml(String customModelXml) {
		this.customModelXml = customModelXml;
	}

	public String getCustomModelXml() {
		return customModelXml;
	}

	public void setProjectType(ProjectDefinition projectType) {
		this.projectType = projectType;
	}

	public ProjectDefinition getProjectType() {
		return projectType;
	}

	public CRUDType getCrudType() {
		return crudType;
	}

	public void setCrudType(CRUDType crudType) {
		this.crudType = crudType;
	}

	@Override
	public List<? extends ProjectCommand> createComand() {
		Optional<ChoosenFrameworkCrudConfiguration> conf = projectType
				.getCrudFrameworkConfiguration();
		if (crudType != null && conf.isPresent()) {
			return Arrays.asList(new CrudGeneratorCommand(conf.get()));
		}
		return Arrays.asList();
	}

	@JsonIgnore
	public CrudModels getCrudModels() {
		Optional<String> modelFile = crudType.getModelFile();

		if (modelFile.isPresent()) {
			return SetupMyProjectXMLReader.read(modelFile.get(),
					CrudModels.class);
		}

		return SetupMyProjectXMLReader.read(new StringReaderXmlSource(
				customModelXml), CrudModels.class);
	}

	@Override
	public String toString() {
		return "CrudForm [crudType=" + crudType + ", projectType="
				+ projectType + ", customModelXml=" + customModelXml + "]";
	}

}

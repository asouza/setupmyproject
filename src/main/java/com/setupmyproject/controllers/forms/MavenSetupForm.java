package com.setupmyproject.controllers.forms;

import java.util.Arrays;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.setupmyproject.commands.MavenBasicConfCommand;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.infra.validator.JavaPackage;
import com.setupmyproject.models.MavenDependency;

public class MavenSetupForm implements CommandGenerator {

	@NotBlank
	@JavaPackage
	private String basePackage;

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	@Override
	public String toString() {
		return "MavenSetupForm [groupId=" + groupId() + ", artifactId="
				+ artifactId() + ", version=" + version() + "]";
	}

	private String version() {
		return "1.0.0-SNAPSHOT";
	}

	public String artifactId() {
		return basePackage.substring(basePackage.lastIndexOf(".")+1);
	}

	private String groupId() {
		return basePackage.substring(0,basePackage.lastIndexOf("."));
	}

	@Override
	public List<? extends ProjectCommand> createComand() {
		return Arrays.asList(new MavenBasicConfCommand(this));
	}

	/**
	 * 
	 * @return o pacote do projeto. Geralmente groupId + artifactId
	 */
	@JsonIgnore
	public String getProjectPackage() {
		return basePackage;
	}

	/**
	 * 
	 * @param requestedPackage
	 *            nome do pacote, dentro da estrutura do projeto. Você pode
	 *            passar <b>models</b>,<b>controllers</b> ou qualquer coisa que
	 *            queira
	 * @return pacote do projeto + requestedPackage
	 */
	public String packageFor(String requestedPackage) {
		return getProjectPackage() + "." + requestedPackage;
	}

	/**
	 * 
	 * @return informações relativas ao maven, do projeto.
	 */
	@JsonIgnore
	public MavenDependency getMavenInfo() {
		if(!basePackage.contains(".")){
			return new MavenDependency(basePackage, basePackage, version()); 
		}
		return new MavenDependency(groupId(), artifactId(), version());
	}

}

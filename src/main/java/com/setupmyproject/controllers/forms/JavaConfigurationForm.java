package com.setupmyproject.controllers.forms;

import java.util.List;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.models.JavaVersion;

public class JavaConfigurationForm implements CommandGenerator{

	private JavaVersion version;

	public JavaVersion getVersion() {
		return version;
	}

	public void setVersion(JavaVersion version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "JavaConfigurationForm [version=" + version + "]";
	}

	@Override
	public List<? extends ProjectCommand> createComand() {
		return MaybeEmptyCommand.generate(version);
	}
	
	
	
	
}

package com.setupmyproject.controllers.forms;

import javax.validation.constraints.NotNull;

import com.setupmyproject.models.SetupType;

public class SetupTypeForm {

	@NotNull
	private SetupType setupType;

	public SetupType getSetupType() {
		return setupType;
	}

	public void setSetupType(SetupType setupType) {
		this.setupType = setupType;
	}

}

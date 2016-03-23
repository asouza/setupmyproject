package com.setupmyproject.models;

import com.setupmyproject.controllers.forms.CommandGenerator;

public interface HasProjectTypeDefinition extends CommandGenerator {

	ProjectDefinition getProjectDefinition();

}

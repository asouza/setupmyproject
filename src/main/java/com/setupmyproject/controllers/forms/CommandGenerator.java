package com.setupmyproject.controllers.forms;

import java.util.List;

import com.setupmyproject.commands.ProjectCommand;

public interface CommandGenerator {

	/**
	 * 
	 * @return sempre um comando. Caso não tenha comando para retornar, retorne um {@link List<EmptyCommand>}.
	 */
	public List<? extends ProjectCommand> createComand();
}

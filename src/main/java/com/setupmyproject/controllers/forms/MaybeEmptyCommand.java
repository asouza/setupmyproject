package com.setupmyproject.controllers.forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.setupmyproject.commands.EmptyCommand;
import com.setupmyproject.commands.ProjectCommand;

class MaybeEmptyCommand {

	/**
	 * 
	 * @param objectToBeVerified
	 *            objeto que deve ser verificado para saber se há necessidade de
	 *            chamar a função
	 * @return o comando gerado pela função ou um {@link EmptyCommand}, caso não
	 *         exista nada.
	 */
	public static List<ProjectCommand> generate(
			List<? extends ProjectCommand> objectToBeVerified) {
		if (objectToBeVerified == null || objectToBeVerified.isEmpty()) {			
			return Lists.newArrayList((new EmptyCommand()));
		}
		
		return new ArrayList<ProjectCommand>(objectToBeVerified);
	}

	public static List<? extends ProjectCommand> generate(ProjectCommand projectCommand) {
		if(projectCommand == null){
			return new ArrayList<ProjectCommand>();
		}
		return generate(Arrays.asList(projectCommand));
	}

}

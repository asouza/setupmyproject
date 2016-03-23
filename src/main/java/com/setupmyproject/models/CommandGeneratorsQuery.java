package com.setupmyproject.models;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.messages.JPAMessages;
import com.setupmyproject.commands.messages.ProjectCommandMessage;
import com.setupmyproject.controllers.forms.CommandGenerator;
import com.setupmyproject.controllers.forms.MavenSetupForm;

public interface CommandGeneratorsQuery {

	/**
	 * 
	 * @param commandClass
	 *            classe do gerador de comando a ser buscado. Geralmente vai ser
	 *            a classe de um formulário. Dê uma olhada em
	 *            {@link MavenSetupForm}
	 * @return
	 */
	<T extends CommandGenerator> T find(Class<T> commandClass);
	
	/**
	 * 
	 * @param commandClass
	 *            classe do gerador de comando a ser buscado. Geralmente vai ser
	 *            a classe de um formulário. Dê uma olhada em
	 *            {@link MavenSetupForm}
	 * @return
	 */
	default <T extends CommandGenerator> Optional<T> ifFind(Class<T> commandClass) {
		return Optional.ofNullable(find(commandClass));
	}

	/**
	 * 
	 * @param projectCommandClass class do comando em si, que implementa a interface {@link ProjectCommand}, que deve ser buscado. 
	 * @return
	 */
	<T extends ProjectCommand> T findProjectCommand(Class<T> projectCommandClass);
	
	/**
	 * 
	 * @param projectCommandClass class do comando em si, que implementa a interface {@link ProjectCommand}, que deve ser buscado. 
	 * @return
	 */
	default <T extends ProjectCommand> Optional<T> ifFindProjectCommand(Class<T> projectCommandClass) {
		return Optional.ofNullable(findProjectCommand(projectCommandClass));
	}

	/**
	 * Recebe mensagem que algum outro comando queira consumir
	 * @param messageType
	 * @param content objeto representando a mensagem. Aqui tem um contrato implicito com quem vai consumir, tome cuidado.
	 */
	void messageToProjectCommand(ProjectCommandMessage messageType, Object content);

	/**
	 * 
	 * @param key chave para recuperar a mensagem
	 * @return
	 */
	Collection<Object> consumeMessage(ProjectCommandMessage key);

}

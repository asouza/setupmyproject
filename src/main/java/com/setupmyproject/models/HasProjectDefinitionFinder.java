package com.setupmyproject.models;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

import com.setupmyproject.controllers.forms.CommandGenerator;

public class HasProjectDefinitionFinder {

	/**
	 * 
	 * @param commandsGenerators lista de objeto que representam cada um dos {@link CommandGenerator}
	 * @return classe do {@link CommandGenerator} que possui a definicao do projeto
	 */
	public static Class<? extends HasProjectTypeDefinition> findThroughInstances(
			Collection<CommandGenerator> commandsGenerators) {
		return findThroughClasses(commandsGenerators.stream()
				.map((commandGenerator) -> commandGenerator.getClass())
				.collect(Collectors.toList()));
	}

	@SuppressWarnings("unchecked")
	/**
	 * 
	 * @param commandsGenerators lista de classes que representam cada um dos {@link CommandGenerator}
	 * @return classe do {@link CommandGenerator} que possui a definicao do projeto
	 */	
	public static Class<? extends HasProjectTypeDefinition> findThroughClasses(
			Collection<Class<?>> commandsGenerators) {
		List<Class<?>> formsWithProjectDefinition = commandsGenerators.stream()				
				.filter(HasProjectTypeDefinition.class::isAssignableFrom)
				.collect(Collectors.toList());

		Assert.state(formsWithProjectDefinition.size() == 1,
				"Você deveria ter apenas um "
						+ HasProjectTypeDefinition.class.getSimpleName()
						+ " no seu wizard. " + formsWithProjectDefinition);

		return (Class<? extends HasProjectTypeDefinition>) formsWithProjectDefinition
				.get(0);
	}
}

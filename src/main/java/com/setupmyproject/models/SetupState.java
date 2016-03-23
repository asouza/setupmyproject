package com.setupmyproject.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

import com.google.common.base.Function;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.ReadmeCommand;
import com.setupmyproject.components.CustomBsonGenerator;
import com.setupmyproject.controllers.forms.CommandGenerator;
import com.setupmyproject.controllers.forms.ConfigurationForm;

public class SetupState {

	private Map<Class<?>, String> setups = new LinkedHashMap<Class<?>, String>();
	private static final CustomBsonGenerator bsonGenerator = new CustomBsonGenerator();

	public SetupState() {
	}

	/**
	 * 
	 * @param previousState
	 *            estado encriptado. Para ter acesso a isso, basta chamar o
	 *            método getValue() do {@link SetupState}
	 */
	public SetupState(String previousState) {
		try {
			recreateState(previousState);
		} catch (Exception e) {
			throw new RuntimeException(
					"Não foi possível recriar o estado da configuracao", e);
		}
	}

	private void recreateState(String previousState) throws Exception {		
		String[] entries = previousState.split(";");
		for (String entry : entries) {
			String[] klassAndBson = entry.split("=");
			Class<?> setupKlass = Class.forName(klassAndBson[0]);
			Object loadedSetup = bsonGenerator
					.read(klassAndBson[1], setupKlass);
			addConf(loadedSetup, (s) -> {
				return null;
			});
		}
	}

	/**
	 * 
	 * @param setupForm
	 *            passo do wizard que deve ser mantido o estado
	 * @param callback
	 *            funcao que deve ser chamado para que sejam atualizadas outras
	 *            partes do sistema
	 * @return
	 */
	public SetupState addConf(Object setupForm,
			Function<SetupState, Void> callback) {
		SetupState newState = addConf(setupForm.getClass(),
				bsonGenerator.write(setupForm));
		callback.apply(newState);
		return newState;
	}

	private SetupState addConf(Class<?> key, String bsonSetupForm) {
		setups.put(key, bsonSetupForm);
		return this;
	}

	public String getValue() {
		StringBuilder builder = new StringBuilder();
		Iterator<Entry<Class<?>, String>> iterator = setups.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<Class<?>, String> next = iterator.next();
			builder.append(next.getKey().getName() + "=" + next.getValue());
			if (iterator.hasNext()) {
				builder.append(";");
			}
		}		
		return builder.toString();
	}

	public ProjectDefinition getProjectType() {
		Class<? extends HasProjectTypeDefinition> formWithProjectDefinitionClass = HasProjectDefinitionFinder
				.findThroughClasses(setups.keySet());
		String bson = setups.get(formWithProjectDefinitionClass);
		HasProjectTypeDefinition hasProjectDefinition = bsonGenerator.read(
				bson, formWithProjectDefinitionClass);
		return hasProjectDefinition.getProjectDefinition();
	}

	@Override
	public String toString() {
		StringBuilder info = new StringBuilder();
		Set<Entry<Class<?>, String>> entries = setups.entrySet();
		for (Entry<Class<?>, String> entry : entries) {
			info.append(bsonGenerator.read(entry.getValue(), entry.getKey()))
					.append(";");
		}
		return info.toString();
	}

	public CommandGenerators getCommandGenerators() {
		HashSet<CommandGenerator> commands = new LinkedHashSet<CommandGenerator>();
		Set<Entry<Class<?>, String>> entries = setups.entrySet();
		for (Entry<Class<?>, String> entry : entries) {
			CommandGenerator generator = (CommandGenerator) bsonGenerator.read(
					entry.getValue(), entry.getKey());
			commands.add(generator);
		}
		CommandGenerator readmeGenerator = new CommandGenerator() {

			@Override
			public List<? extends ProjectCommand> createComand() {
				return new ArrayList<ProjectCommand>(
						Arrays.asList(new ReadmeCommand()));
			}
		};
		commands.add(readmeGenerator);
		return new CommandGenerators(commands);
	}

}

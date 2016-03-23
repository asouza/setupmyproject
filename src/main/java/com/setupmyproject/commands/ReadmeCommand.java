package com.setupmyproject.commands;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.forge.GenericForgeTemplate;
import com.setupmyproject.models.CommandGeneratorsQuery;

public class ReadmeCommand implements ProjectCommand{

	private Set<String> paths = new LinkedHashSet<>();
	private List<String> generatedReadmes = new ArrayList<>();
	private Logger logger = LoggerFactory.getLogger(ReadmeCommand.class);

	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		logger.info("Gerando o readme");
		
		ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);
		GenericForgeTemplate forgeTemplate = forgeHelper.getGenericForgeTemplate();
		generateAllParts(forgeTemplate);
		generateFinalReadme(forgeTemplate,forgeHelper);
	}

	private void generateFinalReadme(GenericForgeTemplate forgeTemplate, ForgeHelper forgeHelper) {
		Map<String,Object> params = new HashMap<>();
		params.put("readmes", generatedReadmes);
		try {
			String result = forgeTemplate.process("/readmes/readme.md",params);
			forgeHelper.saveInRootDirectory("readme.md",result);
		} catch (Exception exception) {
			logger.error("Problema na geracao final do readme, {}",exception);
		}
	}

	private void generateAllParts(GenericForgeTemplate forgeTemplate) {
		for (String path : paths) {
			try {
				generatedReadmes.add(forgeTemplate.process(path,new HashMap<String, String>()));
			} catch (Exception e) {
				logger.error("Problema na geracao do readme {},{}",path,e.getMessage());
			}
		}
	}

	@Override
	public BigDecimal getPrice() {
		return BigDecimal.ZERO;
	}

	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(10, TimeUnit.MINUTES);
	}

	/**
	 * 
	 * @param path caminho para a parte do readme que deve ser concatenada. Ex: /readmes/spring-jpa.md
	 */
	public void addSectionPath(String path) {
		this.paths.add(path);
	}

}

package com.setupmyproject.forge;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.jboss.forge.furnace.Furnace;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.forge.furnace.repositories.AddonRepositoryMode;
import org.jboss.forge.furnace.se.FurnaceFactory;
import org.jboss.forge.furnace.util.OperatingSystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.setupmyproject.conf.AccessEnvironment;

@Component
public class ForgeEngine {

	@Autowired
	private AccessEnvironment env;
	private Logger logger = LoggerFactory.getLogger(ForgeEngine.class);
	private Furnace furnace;

	@PostConstruct
	private void postConstruct() {
		logger.info("Iniciando o forge");
		Furnace furnace = FurnaceFactory.getInstance();

		File customPluginsPath = new File(
				OperatingSystemUtils.getUserForgeDir(), "addons");
		furnace.addRepository(AddonRepositoryMode.MUTABLE, customPluginsPath);

		File defaultPluginsPath = new File(
				OperatingSystemUtils.getUserHomeDir(),
				env.getProperty("forge.path"));
		furnace.addRepository(AddonRepositoryMode.IMMUTABLE, defaultPluginsPath);

		CompletableFuture<Furnace> iniciandoOForge = CompletableFuture.supplyAsync(() -> {
			Future<Furnace> startAsync = furnace.startAsync();
			try {
				return startAsync.get();
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}			
		});
		iniciandoOForge.thenAccept((startedFurnace) -> {
			logger.info("Forge iniciado");
			this.furnace = startedFurnace;
		});
	}

	@PreDestroy
	private void preDestroy() {
		logger.info("Parando o forge");
		furnace.stop();
	}

	public AddonRegistry getAddonRegistry() {
		return furnace.getAddonRegistry();
	}
}

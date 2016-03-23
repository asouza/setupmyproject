package com.setupmyproject.commands;

import java.util.ArrayList;
import java.util.List;

import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;

public class HibernateDependencies {

	public static List<DependencyBuilder> getHibernateDependencies() {
		return getHibernateDependencies("compile");
	}
	
	public static List<DependencyBuilder> getHibernateDependencies(String scope) {
		ArrayList<DependencyBuilder> deps = new ArrayList<>();
		deps.add(DependencyBuilder.create().setGroupId("org.hibernate").setArtifactId("hibernate-entitymanager")
				.setVersion("4.3.0.Final").setScopeType(scope));
		deps.add(DependencyBuilder.create().setGroupId("org.hibernate").setArtifactId("hibernate-core")
				.setVersion("4.3.0.Final").setScopeType(scope));
		deps.add(DependencyBuilder.create().setGroupId("org.hibernate.javax.persistence")
				.setArtifactId("hibernate-jpa-2.1-api").setVersion("1.0.0.Final").setScopeType(scope));
		return deps;
	}

}

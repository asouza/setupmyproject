package com.setupmyproject.models;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.maven.projects.facets.MavenJavaCompilerFacet;
import org.jboss.forge.addon.parser.java.facets.JavaCompilerFacet.CompilerVersion;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.components.ProjectCommandFormItem;

public enum JavaVersion implements ProjectCommandFormItem,Tooltipable{

	JAVA7(CompilerVersion.JAVA_1_7),JAVA8(CompilerVersion.JAVA_1_8);
	
	private CompilerVersion compilerVersion;

	private JavaVersion(CompilerVersion compilerVersion) {
		this.compilerVersion = compilerVersion;
	}
	
	public CompilerVersion getCompilerVersion() {
		return compilerVersion;
	}

	@Override
	public void execute(Project project,AddonRegistry addonRegistry,CommandGeneratorsQuery commandGenerators) {
		MavenJavaCompilerFacet maven = project.getFacet(MavenJavaCompilerFacet.class);
		maven.setSourceCompilerVersion(compilerVersion);
		maven.setTargetCompilerVersion(compilerVersion);
	}
	
	@Override
	public BigDecimal getPrice() {
		return BigDecimal.ZERO;
	}
	
	@Override
	public String getNameKey() {
		return "option."+name();
	}
	
	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(1, TimeUnit.MINUTES);
	}
}

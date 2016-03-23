package com.setupmyproject.forge;

import java.util.List;

import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.addon.projects.facets.DependencyFacet;

import com.setupmyproject.infra.SetupMyProjectXMLReader;
import com.setupmyproject.models.MavenDepencies;

public class ForceDependencyInstaller {

	private DependencyInstaller dependencyInstaller;
	private Project project;
	private DependencyFacet dependencyFacet;

	public ForceDependencyInstaller(DependencyInstaller dependencyInstaller, Project project) {
		this.dependencyInstaller = dependencyInstaller;
		this.project = project;
		dependencyFacet = project
				.getFacet(DependencyFacet.class);
	}
	
	public void forceInstall(String group, String artifact, String version){
		forceInstall(group, artifact, version,"compile");
	}
	
	public void forceInstall(String group, String artifact, String version,String scope){
		forceInstall(DependencyBuilder.create().setGroupId(group)
				.setArtifactId(artifact).setVersion(version)
				.setScopeType(scope));
	}

	public void forceInstall(DependencyBuilder mavenDependency) {
		
		dependencyFacet.removeManagedDependency(mavenDependency);

		if (dependencyInstaller.isInstalled(project, mavenDependency)) {
			dependencyFacet.removeDependency(mavenDependency);			
		}
		
		
		dependencyFacet.addDirectDependency(mavenDependency);
	}

	public void forceInstall(List<DependencyBuilder> deps) {
		deps.stream().forEach(this :: forceInstall);
	}

	public void forceInstall(MavenDepencies deps) {
		forceInstall(deps.getList());
	}
	
	/**
	 * Instala as dependencias que estão configuradas no arquivo xml. Ex: /maven-dependencies/vraptor-basic-deps.xml
	 * @param xmlPath caminho completo para o arquivo. Ex:/maven-dependencies/vraptor-java8-deps.xml
	 */
	public void forceInstallFromXmlFile(String xmlPath) {
		MavenDepencies deps = SetupMyProjectXMLReader.read(xmlPath,MavenDepencies.class);
		forceInstall(deps);
	}
}

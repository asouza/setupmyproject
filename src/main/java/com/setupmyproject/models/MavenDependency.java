package com.setupmyproject.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.springframework.util.StringUtils;

@XmlRootElement(name = "dependency")
@XmlAccessorType(XmlAccessType.FIELD)
public class MavenDependency {

	private String groupId;
	private String artifactId;
	private String version;
	@XmlElement(required = false)
	private String scope = "compile";
	@XmlElement(name="exclusion")
	@XmlElementWrapper(name="exclusions")
	private List<MavenExclusion> exclusions = new ArrayList<MavenExclusion>();	

	/**
	 * pra ser usado pelo Jaxb
	 */
	public MavenDependency() {
	}

	public MavenDependency(String groupId, String artifactId, String version) {
		this(groupId,artifactId,version,"compile");
	}
	
	

	public MavenDependency(String groupId, String artifactId, String version,
			String scope) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.scope = scope;
	}

	public String getGroupId() {
		return groupId;
	}
	
	public String getArtifactId() {
		return artifactId;
	}
	
	public String getVersion() {
		return version;
	}


	public DependencyBuilder toDependencyBuilder() {
		DependencyBuilder dependencyBuilder = DependencyBuilder.create().setGroupId(groupId)
				.setArtifactId(artifactId).setScopeType(scope);
		if(StringUtils.hasText(this.version)){
			dependencyBuilder.setVersion(version);
		}
		
		exclusions.forEach(e -> dependencyBuilder.addExclusion(e.toCoordinate()));
		return dependencyBuilder;
	}
}

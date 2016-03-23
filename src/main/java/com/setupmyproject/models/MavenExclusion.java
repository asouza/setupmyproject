package com.setupmyproject.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.jboss.forge.addon.dependencies.Coordinate;
import org.jboss.forge.addon.dependencies.builder.CoordinateBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
public class MavenExclusion {

	private String artifactId;
	private String groupId;
	
	public MavenExclusion() {
	}

	public MavenExclusion(String artifactId, String groupId) {
		super();
		this.artifactId = artifactId;
		this.groupId = groupId;
	}
	
	public Coordinate toCoordinate() {
		return CoordinateBuilder.create().setArtifactId(artifactId).setGroupId(groupId);
	}
}
